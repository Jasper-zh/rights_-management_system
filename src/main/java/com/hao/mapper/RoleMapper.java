package com.hao.mapper;

import com.hao.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long rid);

    int insert(Role record);

    Role selectByPrimaryKey(Long rid);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);


    void insertRoleAndPermission(@Param("rid") Long rid, @Param("pid") Long pid);

    void deleteRolePermission(@Param("rid") Long rid);

    List<Long> getRoleByEid(Long eid);
}