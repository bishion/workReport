package com.bizi.report.utils;

/**
 * Created by guofangbi on 2016/12/14.
 */
public class ValidateUtil {
    public static boolean isBlank(Object object){
        if(object == null){
            return true;
        }
        if(object instanceof String){
            if(((String) object).length()==0||((String) object).trim().length()==0){
                return true;
            }
        }
        if (object instanceof Number){
            if (((Number) object).intValue()==0){
                return true;
            }
        }
        return false;
    }
}
