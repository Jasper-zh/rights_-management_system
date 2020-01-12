package com.hao.service;

import com.hao.domain.Menu;
import com.hao.domain.PageListRes;
import com.hao.domain.QueryVo;

import java.util.List;

public interface MenuService {

    PageListRes getMenuList(QueryVo vo);

    List<Menu> getAllMenu();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(Long mid);

    List<Menu> getTreeData();
}
