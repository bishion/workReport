package com.bizi.report.dao;

import com.bizi.report.dto.Project;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by guofangbi on 2016/12/14.
 */
public interface IProjectDao {
    @Select("Select * from Project")
    List<Project> queryAll();

    @Insert("Insert into Project values(null,#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Project project);

    @Select("Select * from Project where id=#{id}")
    Project findById(Integer id);

    @Select("Select * from Project where name=#{name}")
    Project findByName(String name);
}
