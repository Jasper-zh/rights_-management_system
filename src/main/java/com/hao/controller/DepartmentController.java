package com.hao.controller;

import com.hao.domain.Department;
import com.hao.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @RequestMapping("/departmentList")
    @ResponseBody
    public List<Department> departmentList(){
        return departmentService.departmentList();
    }

}
