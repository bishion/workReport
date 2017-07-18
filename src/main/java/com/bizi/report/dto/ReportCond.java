package com.bizi.report.dto;

import lombok.Data;

/**
 * Created by guofangbi on 2016/12/1.
 */
@Data
public class ReportCond extends PageCond {
    private String type;
    private String workType;
    private String status;
    private String developer;
    private String startDate;
    private String endDate;
}
