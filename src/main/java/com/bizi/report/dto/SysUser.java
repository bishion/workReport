package com.bizi.report.dto;

import com.bizi.report.utils.SecurityUtil;
import lombok.Data;

/**
 * Created by guofangbi on 2016/11/30.
 */
@Data
public class SysUser {
    private String username;
    private String password;

    public SysUser(String username, String password) {
        this.username = username;
        this.password = SecurityUtil.MD5(password);
    }

    public SysUser() {
    }
}
