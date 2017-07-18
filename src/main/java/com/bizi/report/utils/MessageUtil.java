package com.bizi.report.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guofangbi on 2017/3/18.
 */
@Slf4j
public class MessageUtil {
    public static void sendSms(Map<String, Object> params, String mobiles, String template) {
        if (ValidateUtil.isBlank(mobiles)) {
            log.info("收信人为空.");
            return;
        }
        log.info("收信人列表为:{}", mobiles);
        Map<Object, Object> postData = new HashMap<>();

        try {
            String result = HttpUtil.doRequest(
                    "内部短信URL发送", postData,
                    null, "UTF-8");
            log.info("短信发送完毕，发送结果:{}", result);
        } catch (Exception e) {
            log.error("短信发送异常。", e);
        }
    }
}
