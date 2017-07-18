package com.bizi.report.dto;

import lombok.Data;

/**
 * Created by guofangbi on 2016/11/30.
 */
@Data
public class Report{
    private Integer id;
    private String workType;
    private String developer;
    private String workContent;
    private Integer process;
    private String status;
    private Integer weekNo;
    private String type;
    private String remark;
    private String createDate;
    private String password;
    private Integer projectId;
    private String projectName;
    private String newProject;
}
