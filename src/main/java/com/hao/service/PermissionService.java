package com.hao.service;

import com.hao.domain.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionService {
    List<Permission> permisionList();
    List<Permission> getPermisionsByRid(Long rid);
}
