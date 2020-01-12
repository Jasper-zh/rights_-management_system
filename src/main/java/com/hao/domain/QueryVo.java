package com.hao.domain;

import lombok.Data;

@Data
public class QueryVo {
    Integer page;
    Integer rows;
    String keyword;
}
