package com.hao.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hao.domain.AjaxRes;
import com.hao.domain.Employee;
import com.hao.domain.PageListRes;
import com.hao.domain.QueryVo;
import com.hao.service.EmployeeService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.SystemOutLogger;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/updateState")
    @RequiresPermissions("employee:delete")
    @ResponseBody
    public AjaxRes updateState(Long id){
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employeeService.updateState(id);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("处理成功");
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("处理失败");
        }
       return ajaxRes;
    }
    @RequestMapping("updateEmployee")
    @RequiresPermissions("employee:edit")
    @ResponseBody
    public AjaxRes updateEmployee(Employee employee){
        AjaxRes ajaxRes = new AjaxRes();
        try{
            employeeService.updateEmployee(employee);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("更新成功");
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("更新失败");
        }
        return ajaxRes;
    }
    @RequestMapping("/saveEmployee")
    @RequiresPermissions("employee:add")
    @ResponseBody
    public AjaxRes saveEmployee(Employee employee){
        Md5Hash md5Hash = new Md5Hash(employee.getPassword(), employee.getUsername(), 2);
        employee.setPassword(md5Hash.toString());
        AjaxRes ajaxRes = new AjaxRes();
        try{
            employee.setState(true);
            employeeService.saveEmployee(employee);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("保存成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("保存失败");
        }
        return ajaxRes;
    }
    @RequestMapping("/employeeList")
    @ResponseBody
    public PageListRes employeeList(QueryVo vo){
       PageListRes pageListRes = employeeService.getAllEmployee(vo);
        return pageListRes;
    }
    @RequestMapping("/employee")
    @RequiresPermissions("employee:index")
    public String employee(){
        return "employee";
    }

    @ExceptionHandler(AuthorizationException.class)
    public void handlerShiroException(HandlerMethod method, HttpServletResponse response) throws Exception{
        ResponseBody methodAnnotation=method.getMethodAnnotation(ResponseBody.class);
        if(methodAnnotation!=null){
            AjaxRes ajaxRes = new AjaxRes();
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("您没有权限操作");
            String s = new ObjectMapper().writeValueAsString(ajaxRes);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(s);
        }else {
            response.sendRedirect("notPermission.jsp");
        }
    }



    @RequestMapping("/downloadExcel")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response){
        try {
            System.out.println("---------downloadExcel---------");
            //1.从数据库当中取列表数据
            QueryVo queryVo = new QueryVo();
            queryVo.setPage(1);
            queryVo.setRows(10);
            PageListRes plr = employeeService.getAllEmployee(queryVo);
            List<Employee> employees = (List<Employee>)plr.getRows();
            //2.创建Excel 写到excel当中
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("员工数据");
            //创建一行
            HSSFRow row = sheet.createRow(0);
            //设置行的每一列的数据
            row.createCell(0).setCellValue("编号");
            row.createCell(1).setCellValue("用户名");
            row.createCell(2).setCellValue("入职日期");
            row.createCell(3).setCellValue("电话");
            row.createCell(4).setCellValue("邮件");

            HSSFRow employeeRow = null;
            /*取出每一个员工来去设置数据*/
            for(int i = 0; i < employees.size(); i++){
                Employee employee = employees.get(i);
                employeeRow = sheet.createRow(i+1);
                employeeRow.createCell(0).setCellValue(employee.getId());
                employeeRow.createCell(1).setCellValue(employee.getUsername());
                if (employee.getEntryTime() !=null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String format = sdf.format(employee.getEntryTime());
                    employeeRow.createCell(2).setCellValue(format);
                }else {
                    employeeRow.createCell(2).setCellValue("");
                }
                employeeRow.createCell(3).setCellValue(employee.getPhone());
                employeeRow.createCell(4).setCellValue(employee.getEmail());
            }

            //3.响应给浏览器
            String fileName = new String("员工数据.xls".getBytes("utf-8"), "iso8859-1");
            response.setHeader("content-Disposition","attachment;filename="+fileName);
            wb.write(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*下载模板*/
    @RequestMapping("downloadExcelTpl")
    @ResponseBody
    public void downloadExcelTpl(HttpServletRequest request, HttpServletResponse response){
        FileInputStream is = null;
        try {
            String fileName = new String("EmployeeTpl.xls".getBytes("utf-8"), "iso8859-1");
            response.setHeader("content-Disposition","attachment;filename="+fileName);
            /*获取文件路径*/
            String realPath = request.getSession().getServletContext().getRealPath("static/ExcelTml.xls");
            is = new FileInputStream(realPath);
            IOUtils.copy(is,response.getOutputStream());

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*配置文件上传解析器  mvc配置当中*/
    @RequestMapping("/uploadExcelFile")
    @ResponseBody
    public AjaxRes uploadExcelFile(MultipartFile excel){
        System.out.println("--------------------------文件上传----------------------------");
        AjaxRes ajaxRes = new AjaxRes();
        try {
            ajaxRes.setMsg("导入成功");
            ajaxRes.setSuccess(true);
            HSSFWorkbook wb = new HSSFWorkbook(excel.getInputStream());
            HSSFSheet sheet = wb.getSheetAt(0);
            /*获取最大的行号*/
            int lastRowNum = sheet.getLastRowNum();
            Row employeeRow = null;
            for (int i=1; i <= lastRowNum; i++){
                employeeRow = sheet.getRow(i);
                Employee employee = new Employee();
                System.out.println(getCellValue(employeeRow.getCell(0)));
                System.out.println(getCellValue(employeeRow.getCell(1)));
                System.out.println(getCellValue(employeeRow.getCell(2)));
                System.out.println(getCellValue(employeeRow.getCell(3)));
                System.out.println(getCellValue(employeeRow.getCell(4)));
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxRes.setMsg("导入失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }
    private Object getCellValue(Cell cell){
        switch (cell.getCellType()) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
        }
        return cell;
    }

}
