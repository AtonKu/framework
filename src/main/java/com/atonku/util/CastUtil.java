package com.atonku.util;

/**
 * @Description: 数据类型转换工具类
 * @Date: 2018/4/8 15:26
 * @Author: GYT
 * @Modified by:
 **/
public final class CastUtil {

    /**
     * 调用该类中的方法时，如果调用的是不提供默认值的方法，则会在方法内部调用设置默认值的方法，
     * 并使用方法中设置的默认值，返回对应的结果
     */

    /**
     * 转为String类型
     * @param object
     * @return
     */
    public static String castString(Object object) {
        return CastUtil.castString(object, "");
    }

    /**
     * 转为String类型, 提供默认值
     * @param object
     * @param defaultValue
     * @return
     */
    public static String castString(Object object, String defaultValue) {
        return object != null ? String.valueOf(object) : defaultValue;
    }

    /**
     * 转为double类型
     * @param object
     * @return
     */
    public static double castDouble(Object object) {
        return CastUtil.castDouble(object, 0);
    }

    /**
     * 转为double类型, 提供默认值
     * @param object
     * @param defaultValue
     * @return
     */
    private static double castDouble(Object object, double defaultValue) {
        double value = defaultValue;
        if (object != null) {
            String str = castString(object);
            if (StringUtil.isNotEmpty(str)) {
                try {
                    value = Double.parseDouble(str);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转为long类型
     * @param object
     * @return
     */
    public static long castLong(Object object) {
        return CastUtil.castLong(object, 0);
    }

    /**
     * 转为long类型, 提供默认值
     * @param object
     * @param defaultValue
     * @return
     */
    public static long castLong(Object object, long defaultValue) {
        long value = defaultValue;
        if (object != null) {
            String str = castString(object);
            if (StringUtil.isNotEmpty(str)) {
                try {
                    value = Long.parseLong(castString(object));
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转换为int类型
     * @param object
     * @return
     */
    public static int castInt(Object object) {
        return CastUtil.castInt(object, 0);
    }

    /**
     * 转换为int类型, 提供默认值
     * @param object
     * @param defaultValue
     * @return
     */
    public static int castInt(Object object, int defaultValue) {
        int value = defaultValue;
        if (object != null) {
            String str = castString(object);
            if (StringUtil.isNotEmpty(str)) {
                try {
                    value = Integer.parseInt(castString(object));
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转为boolean类型
     * @param object
     * @return
     */
    public static boolean castBoolean(Object object) {
        return CastUtil.castBoolean(object, false);
    }

    /**
     * 转为boolean类型, 提供默认值
     * @param object
     * @param defaultValue
     * @return
     */
    public static boolean castBoolean(Object object, boolean defaultValue) {
        boolean value = defaultValue;
        if (object != null) {
            value = Boolean.parseBoolean(castString(object));
        }
        return value;
    }

}
