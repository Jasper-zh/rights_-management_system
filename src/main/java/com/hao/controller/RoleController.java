package com.hao.controller;

import com.hao.domain.AjaxRes;
import com.hao.domain.PageListRes;
import com.hao.domain.QueryVo;
import com.hao.domain.Role;
import com.hao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {
    @Autowired
    RoleService roleService;
    @RequestMapping("getRoleByEid")
    @ResponseBody
    public List<Long> getRoleByEid(Long eid){
        return roleService.getRoleByEid(eid);
    }
    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxRes deleteRole(Long rid){
        AjaxRes ajaxRes = new AjaxRes();
        try {
            roleService.deleteRole(rid);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("删除角色成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("删除角色失败");
        }
        return ajaxRes;
    }
    @RequestMapping("/updateRole")
    @ResponseBody
    public AjaxRes updateRole(Role role){
        AjaxRes ajaxRes = new AjaxRes();
        try {
            roleService.updateRole(role);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("更新角色成功");
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("更新角色失败");
        }
        return ajaxRes;
    }
    @RequestMapping("saveRole")
    @ResponseBody
    public AjaxRes saveRole(Role role){
        AjaxRes ajaxRes = new AjaxRes();
        try {
            roleService.saveRole(role);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("添加角色成功");
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("添加角色失败");
        }
        return ajaxRes;
    }
    @RequestMapping("/getAllRole")
    @ResponseBody
    public List<Role> getAllRole(){
        return roleService.getAllRole();
    }
    @RequestMapping("/roleList")
    @ResponseBody
    public PageListRes roleList(QueryVo vo){
        return roleService.roleList(vo);
    }
    @RequestMapping("/role")
    public String role(){
        return "role";
    }

}
