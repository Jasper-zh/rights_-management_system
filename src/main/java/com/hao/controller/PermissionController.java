package com.hao.controller;

import com.hao.domain.Permission;
import com.hao.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PermissionController {
    @Autowired
    PermissionService permissionService;
    @RequestMapping("/getPermissionsByRole")
    @ResponseBody
    public List<Permission> getPermissionsByRole(Long rid){
        List<Permission> permissions = permissionService.getPermisionsByRid(rid);
        System.out.println(permissions);
        return permissions;
    }
    @RequestMapping("/permissionList")
    @ResponseBody
    public List<Permission> permissionList(){
        return permissionService.permisionList();
    }
}
