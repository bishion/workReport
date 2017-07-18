package com.bizi.report.dao;

import com.bizi.report.dto.Report;
import com.bizi.report.dto.ReportCond;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by guofangbi on 2016/11/30.
 */
public interface IReportDao {
    @Select("select * from report where id=#{id}")
    Report findReport(Integer id);

    @Insert("insert into report values(null,#{workType},#{developer},#{workContent},#{process},#{status},#{weekNo},#{type},#{remark},now(),#{projectId},#{projectName})")
    void insertReport(Report report);

    void batchInsert(List<Report> reportList);

    @Select("delete from report where id=#{id} and developer=#{developer}")
    void deleteReport(Report report);

    List<Report> queryReport(ReportCond report);
    Integer countReport(ReportCond report);

    void updateReport(Report report);
}
