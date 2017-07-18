package com.bizi.report.dao;

import com.bizi.report.dto.Developer;
import com.bizi.report.dto.ReportCond;

import java.util.List;

/**
 * Created by guofangbi on 2017/3/18.
 */

public interface IDeveloperDao {
    List<Developer> queryNotReported(ReportCond report);
}
