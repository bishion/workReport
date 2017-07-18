package com.bizi.report.dao;

import com.bizi.report.dto.BugReport;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * Created by guofangbi on 2017/3/21.
 */
public interface IBugReportDao {
    @Insert("insert into bug_report values(null,#{type},#{menu},#{description},#{status},#{reporter},now(),#{reporter},now(),#{remark})")
    void insert(BugReport bugReport);

    void update(BugReport bugReport);

    List<BugReport> query(BugReport bugReport);
    int count(BugReport bugReport);
}
