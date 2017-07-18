package com.bizi.report.dto;

import lombok.Data;

/**
 * Created by guofangbi on 2017/3/21.
 */
@Data
public class BugReport {
    private Integer id;
    private String type;
    private String menu;
    private String description;
    private String status;
    private String reporter;
    private String startDate;
    private String endDate;
    private String createDate;
    private String modifier;
    private String remark;
    private int start;
    private int limit;
}
