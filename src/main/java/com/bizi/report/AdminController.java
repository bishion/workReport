package com.bizi.report;

import com.bizi.report.excel.WriteExcel;
import com.bizi.report.utils.DateUtil;
import com.bizi.report.dao.IReportDao;
import com.bizi.report.dto.Report;
import com.bizi.report.dto.ReportCond;
import com.bizi.report.consts.BaseConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Resource
    private IReportDao reportDao;
    @Resource
    private HttpServletResponse response;
    @RequestMapping("/download.json")
    public void download(ReportCond cond){
        cond.setLimit(Integer.MAX_VALUE);
        List<Report> reportList = reportDao.queryReport(cond);

        try {
            String fileName = DateUtil.toString(new Date(),DateUtil.yyyy_MM_dd)+".xls";
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",String.format("attachment; filename=\"%s\"",fileName));
            WriteExcel.writeToStream(reportList, BaseConst.attrs,BaseConst.heads,response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            log.error("文件下载失败",e);
        }


    }
}
