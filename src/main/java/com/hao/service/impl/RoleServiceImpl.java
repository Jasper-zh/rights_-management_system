package com.hao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hao.domain.PageListRes;
import com.hao.domain.Permission;
import com.hao.domain.QueryVo;
import com.hao.domain.Role;
import com.hao.mapper.RoleMapper;
import com.hao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public PageListRes roleList(QueryVo vo) {
        Page<Role> page = PageHelper.startPage(vo.getPage(),vo.getRows());
        List<Role> roles = roleMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(roles);
        return pageListRes;
    }

    @Override
    public List<Role> getAllRole() {
        return roleMapper.selectAll();
    }


    @Override
    public void saveRole(Role role) {
        //保存角色
        roleMapper.insert(role);
        //保存角色与权限关系
        for (Permission permission : role.getPermissions() ) {
            roleMapper.insertRoleAndPermission(role.getRid(),permission.getPid());
        }
    }

    @Override
    public void updateRole(Role role) {
        //删除以前角色权限关系
        roleMapper.deleteRolePermission(role.getRid());
        //更新角色
        roleMapper.updateByPrimaryKey(role);
        //更新关系
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRoleAndPermission(role.getRid(),permission.getPid());
        }
    }

    @Override
    public void deleteRole(Long rid) {
        //删除关系
        roleMapper.deleteRolePermission(rid);
        //删除角色
        roleMapper.deleteByPrimaryKey(rid);
    }

    @Override
    public List<Long> getRoleByEid(Long eid) {
        return roleMapper.getRoleByEid(eid);
    }
}
