package com.hao.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageListRes {
    private Long total;
    private List<?> rows = new ArrayList<>();
}
