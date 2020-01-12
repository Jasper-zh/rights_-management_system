package com.hao.service.impl;

import com.hao.domain.Permission;
import com.hao.mapper.PermissionMapper;
import com.hao.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public List<Permission> permisionList() {
        return permissionMapper.selectAll();
    }

    @Override
    public List<Permission> getPermisionsByRid(Long rid) {

         return permissionMapper.getPermissionsByRid(rid);
    }
}
