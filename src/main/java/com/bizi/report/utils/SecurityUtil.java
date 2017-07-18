package com.bizi.report.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * Created by guofangbi on 2016/12/1.
 */
@Slf4j
public class SecurityUtil {
    public static String MD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(content.getBytes());
            return getStr(result);

        } catch (Exception e) {
            log.error("加密异常",e);
        }
        return null;
    }

    /**
     * 把字节数组转化成字符串返回
     *
     * @param bytes
     * @return
     */
    private static String getStr(byte[] bytes) {

        StringBuffer sb = new StringBuffer();
        for (byte aByte : bytes) {
            String s = Integer.toHexString(0xff & aByte);
            if (s.length() == 1) {
                sb.append("0" + s);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }
}
