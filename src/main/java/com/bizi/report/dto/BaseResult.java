package com.bizi.report.dto;


import lombok.Data;

/**
 * Created by guofangbi on 2016/11/30.
 */
@Data
public class BaseResult<T> {
    private boolean success = true;
    private String message;
    private T value;

    public BaseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }public BaseResult() {
    }
    public BaseResult(T value){
        this.success = true;
        this.value   = value;
    }
}
