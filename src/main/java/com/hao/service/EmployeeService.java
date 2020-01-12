package com.hao.service;

import com.hao.domain.Employee;
import com.hao.domain.PageListRes;
import com.hao.domain.QueryVo;
import com.hao.domain.Role;

import java.util.List;

public interface EmployeeService {
    PageListRes getAllEmployee(QueryVo vo);
    void saveEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void updateState(Long id);
    Employee getEmployeeByName(String username);
    List<String> getRolesByEid(Long id);
    List<String> getPermissionsByEid(Long id);
}
