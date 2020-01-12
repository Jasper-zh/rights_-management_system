package com.hao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hao.domain.Employee;
import com.hao.domain.Menu;
import com.hao.domain.PageListRes;
import com.hao.domain.QueryVo;
import com.hao.mapper.MenuMapper;
import com.hao.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Override
    public PageListRes getMenuList(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Menu> list = menuMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(list);
        return pageListRes;
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.selectAll();
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public void deleteMenu(Long mid) {
        menuMapper.deleteByPrimaryKey(mid);
    }

    @Override
    public List<Menu> getTreeData() {

        List<Menu> treeData = menuMapper.getTreeData();
        /*
        判断当前用户有没有对应的权限
        如果没有就从集合当中移除
        */
        /*获取用户 判断用户是否是管理员 是管理就不需要做判断*/
        Subject subject = SecurityUtils.getSubject();
        /*当前的用户*/
        Employee employee = (Employee)subject.getPrincipal();
        if (!employee.getAdmin()){
            /*做检验权限*/
            checkPermission(treeData);
        }

        return treeData;
    }

    public void checkPermission(List<Menu> menus){
        //获取主体
        Subject subject = SecurityUtils.getSubject();
        //遍历所有的菜单及子菜单
        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()){
            Menu menu = iterator.next();
            if (menu.getPermission() !=null){
                //判断当前menu是否有权限对象,如果说没有 当前遍历的菜单从集合当中移除
                String presource = menu.getPermission().getPresource();
                if (!subject.isPermitted(presource)){
                    //当前遍历的菜单从集合当中移除
                    iterator.remove();
                    continue;
                }
            }
            /*判断是否有子菜单  有子菜单也要做权限检验*/
            if (menu.getChildren().size() > 0){
                checkPermission(menu.getChildren());
            }
        }
    }
}
