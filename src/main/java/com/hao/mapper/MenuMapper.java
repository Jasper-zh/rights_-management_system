package com.hao.mapper;

import com.hao.domain.Menu;
import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Long mid);

    int insert(Menu record);

    Menu selectByPrimaryKey(Long mid);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);

    List<Menu> getTreeData();
}