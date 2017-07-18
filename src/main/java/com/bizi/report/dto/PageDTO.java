package com.bizi.report.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofangbi on 2016/12/1.
 */
@Data
public class PageDTO<E> {
    /**
     * 每页条数
     */
    private int limit = 20;

    /**
     * 起始条数
     */
    private int start = 0;

    /**
     * 记录总数
     */
    private long results = 0;
    /**
     * 结果列表数据
     */
    private List<E> rows = new ArrayList<E>();
    private boolean hasError = false;
}
