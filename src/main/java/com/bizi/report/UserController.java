package com.bizi.report;

import com.bizi.report.utils.DateUtil;
import com.bizi.report.dao.IUserDao;
import com.bizi.report.dto.BaseResult;
import com.bizi.report.dto.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by guofangbi on 2016/12/1.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserDao userDao;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/insert.json")
    public BaseResult insert(String username, String password) {
        if (username == null || password == null) {
            return new BaseResult(false, "参数为空");
        }
        SysUser user = new SysUser(username, password);
        userDao.insertUser(user);
        return new BaseResult();
    }
    @RequestMapping("/getName.json")
    public BaseResult<String> getName(){
       /* SysUser user = SsoSession.getCurrentUser(request);
        return new BaseResult<String>(true,user==null?null:user.getUsername());*/
       return null;
    }
    @RequestMapping("/getWeekNo.json")
    public BaseResult<String> getWeekNo(){
        return new BaseResult<String>(true, String.valueOf(DateUtil.getWeekNo(new Date())));
    }
}
