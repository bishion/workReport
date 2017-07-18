package com.bizi.report.dto;

import lombok.Data;

/**
 * Created by guofangbi on 2016/12/14.
 */
@Data
public class Project {
    private Integer id;
    private String name;
    public Project(String name){
        this.name = name;
    }
    public Project(){
    }
}
