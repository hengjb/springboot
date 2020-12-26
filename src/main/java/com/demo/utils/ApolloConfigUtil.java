package com.demo.utils;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 配置管理
 *
 * @author Ji.huazhen
 */
@Component
@Slf4j
public class ApolloConfigUtil {

    @ApolloConfig
    private Config config;
    /**
     * 获取配置值
     *
     * @param key
     * @param defaultVal
     * @return
     */
    public String getVal(String key, String defaultVal) {
        if (StringUtils.isBlank(key)) {
            log.warn("[配置值Key或DefaultVal未存在],Key={},DefaultVal={}", key, defaultVal);
            return defaultVal;
        }
        try {
            String value = config.getProperty(key, defaultVal);
            if (StringUtils.isBlank(value)) {
                log.error("[配置值Key为空],Key={}", key);
                return null;
            }
            return value;
        } catch (Exception e) {
            log.error("[获取配置异常],Key={}", key);
            log.error("[获取配置异常],Exception = {}", e);
        }
        return null;
    }
}
