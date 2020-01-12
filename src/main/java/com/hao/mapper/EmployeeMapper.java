package com.hao.mapper;

import com.hao.domain.Employee;
import com.hao.domain.QueryVo;
import com.hao.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll(QueryVo vo);

    int updateByPrimaryKey(Employee record);

    void updateState(Long id);

    void insertEmployeeAndRole(@Param("id") Long id,@Param("rid") Long rid);

    void deleteEmployeeRole(Long id);

    Employee getEmployeeByName(String username);

    List<String> getRolesByEid(Long id);

    List<String> getPermissionByEid(Long id);
}
