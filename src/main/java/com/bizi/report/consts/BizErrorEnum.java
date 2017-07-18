package com.bizi.report.consts;

/**
 * Created by guofangbi on 2016/12/13.
 */
public enum BizErrorEnum {
    SYS_PARAM_BLANK("SYS001","参数为空"),
    XML_PARSE_ERROR("XML001","xml数据解析失败"),
    XML_XPATH_ERROR("XML002","XPath解析错误"),
    PRJ_IS_BLANK("PRJ001","项目未填写"),
    PRJ_NOT_EXIST("PRJ002","项目不存在"),
    LGN_INFO_ERROR("LGN001", "用户名密码错误");

    BizErrorEnum(String code, String message){
        this.message = message;
        this.code = code;
    }
    private String message;
    private String code;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
