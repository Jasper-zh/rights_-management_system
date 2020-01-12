package com.hao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hao.domain.Employee;
import com.hao.domain.PageListRes;
import com.hao.domain.QueryVo;
import com.hao.domain.Role;
import com.hao.mapper.EmployeeMapper;
import com.hao.service.EmployeeService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    @Override
    public PageListRes getAllEmployee(QueryVo vo) {

        Page<Object> page = PageHelper.startPage(vo.getPage(),vo.getRows());
        List<Employee> list = employeeMapper.selectAll(vo);
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(list);
        return pageListRes;
    }


    @Transactional
    @Override
    public void saveEmployee(Employee employee) {
        //保存员工
        employeeMapper.insert(employee);
        //保存员工角色关系
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmployeeAndRole(employee.getId(),role.getRid());
        }

    }
    @Transactional
    @Override
    public void updateEmployee(Employee employee) {
        //删除以前关系
        employeeMapper.deleteEmployeeRole(employee.getId());
        //更新员工
        employeeMapper.updateByPrimaryKey(employee);
        //添加新关系
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmployeeAndRole(employee.getId(),role.getRid());
        }
    }

    @Override
    public void updateState(Long id) {
        employeeMapper.updateState(id);
    }

    @Override
    public Employee getEmployeeByName(String username) {
        Employee employee =  employeeMapper.getEmployeeByName(username);
        System.out.println(username+"-----------service------------"+employee);
        return employee;
    }

    @Override
    public List<String> getRolesByEid(Long id) {
        List<String> roles = employeeMapper.getRolesByEid(id);
        return roles;
    }

    @Override
    public List<String> getPermissionsByEid(Long id) {

       return employeeMapper.getPermissionByEid(id);
    }
}
