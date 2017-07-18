package com.bizi.report.exception;

import com.bizi.report.consts.BizErrorEnum;
import lombok.Data;

/**
 * Created by guofangbi on 2016/12/1.
 */
@Data
public class BaseException extends Exception {
    private BizErrorEnum errorEnum;

    public BaseException(BizErrorEnum errorEnum){
        this.errorEnum = errorEnum;
    }
    public BaseException(BizErrorEnum errorEnum, Throwable e){
        super(e);
        this.errorEnum = errorEnum;
    }
}
