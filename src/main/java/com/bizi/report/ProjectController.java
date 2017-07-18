package com.bizi.report;

import com.bizi.report.dao.IProjectDao;
import com.bizi.report.dto.Project;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private IProjectDao projectDao;
    @RequestMapping("/queryAll.json")
    public List<Project> queryAll() {
        return projectDao.queryAll();
    }
}
