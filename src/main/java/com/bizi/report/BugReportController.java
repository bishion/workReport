package com.bizi.report;

import com.bizi.report.dao.IBugReportDao;
import com.bizi.report.dto.BaseResult;
import com.bizi.report.dto.BugReport;
import com.bizi.report.dto.PageDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by guofangbi on 2017/3/21.
 */
@RestController
@RequestMapping("/bugReport")
public class BugReportController {
    @Resource
    private IBugReportDao bugReportDao;

    @RequestMapping("/insert.json")
    public BaseResult<Void> insert(BugReport bugReport){
        String loginName = "郭芳碧";
        bugReport.setReporter(loginName);
        bugReportDao.insert(bugReport);
        return new BaseResult<>(true,null);
    }

    @RequestMapping("/query.json")
    public PageDTO<BugReport> query(BugReport bugReport){
        PageDTO<BugReport> result = new PageDTO<>();
        int count = bugReportDao.count(bugReport);
        result.setResults(count);
        if(count>0){
            result.setRows(bugReportDao.query(bugReport));
        }
        return result;
    }
    @RequestMapping("/update.json")
    public BaseResult<Void> update(BugReport bugReport){
        String loginName = "郭芳碧";
        bugReport.setReporter(loginName);
        bugReportDao.update(bugReport);
        return new BaseResult<>(true,null);
    }
}
