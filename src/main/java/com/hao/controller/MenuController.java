package com.hao.controller;

import com.hao.domain.AjaxRes;
import com.hao.domain.Menu;
import com.hao.domain.PageListRes;
import com.hao.domain.QueryVo;
import com.hao.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MenuController {
    @Autowired
    MenuService menuService;

    @RequestMapping("/getTreeData")
    @ResponseBody
    public List<Menu> getTreeData(){
        return menuService.getTreeData();
    }

    @RequestMapping("/deleteMenu")
    @ResponseBody
    public AjaxRes deleteMenu(Long id) {
        AjaxRes ajaxRes = new AjaxRes();
        try{
            menuService.deleteMenu(id);
            System.out.println("——————————————————————————————————————————"+id);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("菜单项删除成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("菜单项删除失败");
        }
        return ajaxRes;
    }
    @RequestMapping("/updateMenu")
    @ResponseBody
    public AjaxRes updateMenu(Menu menu){
        AjaxRes ajaxRes = new AjaxRes();
        try{
            menuService.updateMenu(menu);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("菜单项修改成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("菜单项修改失败");
        }
        return ajaxRes;
    }
    @RequestMapping("/saveMenu")
    @ResponseBody
    public AjaxRes saveMenu(Menu menu){
        AjaxRes ajaxRes = new AjaxRes();
        try{
            menuService.saveMenu(menu);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("菜单项保存成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("菜单项保存失败");
        }
        return ajaxRes;
    }
    @RequestMapping("/getAllMenu")
    @ResponseBody
    public List<Menu> getAllMenu(){
        return menuService.getAllMenu();
    }

    @RequestMapping("/menuList")
    @ResponseBody
    public PageListRes menuList(QueryVo vo){

        return menuService.getMenuList(vo);
    }
    @RequestMapping("/menu")
    public String menu(){
        return "menu";
    }
}
