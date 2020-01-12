package com.hao.service;

import com.hao.domain.PageListRes;
import com.hao.domain.QueryVo;
import com.hao.domain.Role;
import com.sun.rowset.internal.Row;

import java.util.List;


public interface RoleService {
    PageListRes roleList(QueryVo vo);
    List<Role> getAllRole();
    void saveRole(Role role);
    void updateRole(Role role);
    void deleteRole(Long rid);
    List<Long> getRoleByEid(Long eid);
}
