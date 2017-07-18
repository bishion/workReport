package com.bizi.report;

import com.bizi.report.consts.BizErrorEnum;
import com.bizi.report.exception.BaseException;
import com.bizi.report.utils.DateUtil;
import com.bizi.report.utils.ValidateUtil;
import com.bizi.report.dao.IProjectDao;
import com.bizi.report.dao.IReportDao;
import com.bizi.report.dao.IUserDao;
import com.bizi.report.dto.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by guofangbi on 2016/11/30.
 */
@RestController
@RequestMapping("/work")
@Slf4j
public class WorkController {
    @Resource
    private IReportDao reportDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private IProjectDao projectDao;

    @Autowired
    private HttpServletRequest request;
    @RequestMapping("/insert.json")
    public BaseResult insert(Report report) {
        try {
            checkLogin(report);
            checkProject(report);
            report.setWeekNo(DateUtil.getWeekNo(new Date()));

            reportDao.insertReport(report);
            return new BaseResult();
        } catch (Exception e) {
            log.error("插入异常",e);
            return new BaseResult(false, e.getMessage());
        }

    }

    private void checkProject(Report report) throws BaseException {
        if(ValidateUtil.isBlank(report.getProjectId())){
            if(ValidateUtil.isBlank(report.getNewProject())){
                throw new BaseException(BizErrorEnum.PRJ_IS_BLANK);
            }
            // 防止重复插入
            Project project = projectDao.findByName(report.getNewProject());
            if(ValidateUtil.isBlank(project)){
                project = new Project(report.getNewProject());
                projectDao.insert(project);
            }

            report.setProjectId(project.getId());
            report.setProjectName(project.getName());

        }else{
            Project project = projectDao.findById(report.getProjectId());
            if(ValidateUtil.isBlank(project)){
                throw new BaseException(BizErrorEnum.PRJ_NOT_EXIST);
            }
            report.setProjectName(project.getName());
        }
    }

    @RequestMapping("/query.json")
    public PageDTO<Report> query(ReportCond cond) {
        PageDTO<Report> result = new PageDTO();
        int count = reportDao.countReport(cond);
        result.setResults(count);
        if (count == 0) {
            return result;
        }
        result.setRows(reportDao.queryReport(cond));
        return result;
    }

    @RequestMapping("/update.json")
    public BaseResult update(Report report) {
        try {
            checkLogin(report);
            checkProject(report);
            reportDao.updateReport(report);
            return new BaseResult();
        } catch (Exception e) {
            return new BaseResult(false, e.getMessage());
        }
    }

    @RequestMapping("/delete.json")
    public BaseResult delete(Report report) {
        try {
            checkLogin(report);
            reportDao.deleteReport(report);
            return new BaseResult();
        } catch (Exception e) {
            return new BaseResult(false, e.getMessage());
        }
    }

    private void checkLogin(Report report) throws BaseException {
        if (report == null) {
            throw new BaseException(BizErrorEnum.SYS_PARAM_BLANK);
        }
       /* SysUser user = SsoSession.getCurrentUser(request);
        if(user == null){
            throw new BaseException(BizErrorEnum.LGN_INFO_ERROR);
        }
        report.setDeveloper(user.getUsername());*/
    }

}
