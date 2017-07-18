package com.bizi.report.service;

import com.bizi.report.consts.BaseConst;
import com.bizi.report.excel.WriteExcel;
import com.bizi.report.mail.MailSender;
import com.bizi.report.utils.DateUtil;
import com.bizi.report.utils.MessageUtil;
import com.bizi.report.dao.IDeveloperDao;
import com.bizi.report.dao.IReportDao;
import com.bizi.report.dto.Developer;
import com.bizi.report.dto.Report;
import com.bizi.report.dto.ReportCond;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by guofangbi on 2017/3/18.
 */
@Component
public class TaskService {
    @Resource
    private IDeveloperDao developerDao;
    @Resource
    private IReportDao reportDao;

    @Scheduled(cron = "0 30 10 ? * FRI")// 周五上午十点半提醒大家填周报
    public void remindNotReported(){
        String notReported = getNotReported();
        MessageUtil.sendSms(null, notReported, "REPORT_SMS");
    }
    @Scheduled(cron = "0 50 15 ? * FRI")//语音电话提醒未填周报的人抓紧写
    public void notifyNoReported() {
        String notReported = getNotReported();
        MessageUtil.sendSms(null, notReported, "REPORT_CALL");
    }

    @Scheduled(cron = "0 0 17 ? * FRI")//给管理员发送周报附件
    public void sendReportToAdmin() {
        // 获取没有写周报的人员列表
        List<Developer> developers = getNotReportedDevelopers();
        StringBuilder sb = new StringBuilder("本周周报填写情况：");
        if(CollectionUtils.isEmpty(developers)){
            sb.append("所有人都已填写。");
        }else {
            sb.append("部分人未填写。\n未填写的组员：");
            for (Developer developer : developers){
                sb.append(developer.getDeveloper()).append(",");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        // 生成excel
        List<Report> reportList = getReport();
        InputStream inputStream = WriteExcel.writeToInputStream(reportList, BaseConst.attrs,BaseConst.heads);
        // 发送邮件
        String todayStr = DateUtil.toString(new Date(),DateUtil.yyyyMMdd);
        String title = todayStr+"周报";
        String fileName = todayStr+".xls";
        MailSender.sendMail("admin@***.com",title,sb.toString(),fileName,inputStream);
    }
    private String getNotReported(){
        List<Developer> developers = getNotReportedDevelopers();
        if (CollectionUtils.isEmpty(developers)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Developer developer : developers) {
            sb.append(developer.getPhone()).append(",");
        }
        return sb.toString();
    }
    private List<Developer> getNotReportedDevelopers() {
        // 获取本周第一天
        String firstDayOfThisWeek = DateUtil.toString(DateUtil.getFirstDayOfThisWeek());

        String endDate = DateUtil.toString(new Date());

        ReportCond reportCond = new ReportCond();
        reportCond.setStartDate(firstDayOfThisWeek);
        reportCond.setEndDate(endDate);

        return developerDao.queryNotReported(reportCond);
    }

    private List<Report> getReport(){
        String startDate = DateUtil.toString(DateUtil.getFirstDayOfThisWeek());
        String endDate = DateUtil.toString(new Date());

        ReportCond reportCond = new ReportCond();
        reportCond.setStartDate(startDate);
        reportCond.setEndDate(endDate);
        reportCond.setLimit(Integer.MAX_VALUE);
        return reportDao.queryReport(reportCond);
    }

}
