package com.example.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author Sourire
 * 封装了查询条件的分页
 */
@Data
public class QueryEntity<T> extends Page<T> {

    private String datemin;
    private String datemax;
    private String queryMsg;
}
