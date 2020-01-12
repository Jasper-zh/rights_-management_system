package com.hao.service.impl;

import com.hao.domain.Department;
import com.hao.mapper.DepartmentMapper;
import com.hao.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;
    @Override
    public List<Department> departmentList() {
       List<Department> departments = departmentMapper.selectAll();
        return departments;
    }
}
