package com.hao.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {
    private Long id;

    private String text;

    private String url;

    private Menu parentMenu;

    private Permission permission;

    private List<Menu> children = new ArrayList<>();
}