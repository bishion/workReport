package com.bizi.report.dao;

import com.bizi.report.dto.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by guofangbi on 2016/11/30.
 */
public interface IUserDao {


    @Insert("insert sys_user(id,username,password) values(null,#{username},#{password})")
    void insertUser(SysUser user);


    @Select("select * from sys_user")
    List<SysUser> findUserList();

    @Select("select * from sys_user where username=#{username} and password=#{password}")
    SysUser login(SysUser user);
}
