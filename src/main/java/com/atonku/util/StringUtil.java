package com.atonku.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 字符串工具类
 * @Date: 2018/4/8 15:48
 * @Author: GYT
 * @Modified by:
 **/
public final class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
