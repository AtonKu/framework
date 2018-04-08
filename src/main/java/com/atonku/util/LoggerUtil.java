//package com.atonku.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * @Description: Logger工具类
// * @Date: 2018/4/8 16:59
// * @Author: GYT
// * @Modified by:
// **/
//public class LoggerUtil {
//
//    private Logger logger;
//
//    /**
//     * 构造方法，初始化Log4j的日志对象
//     */
//    private LoggerUtil(Logger log4jLogger) {
//        logger = log4jLogger;
//    }
//
//    /**
//     * 获取构造器，根据类初始化Logger对象
//     *
//     * @param clazz Class对象
//     * @return Logger对象
//     */
//    public static Logger getLogger(Class clazz) {
//        return LoggerFactory.getLogger(clazz);
//    }
//
//    /**
//     * 获取构造器，根据类名初始化Logger对象
//     *
//     * @param loggerName 类名字符串
//     * @return Logger对象
//     */
//    public static Logger getLogger(String loggerName) {
//        return LoggerFactory.getLogger(loggerName);
//    }
//
//    public void debug(Object object) {
//        logger.debug(object);
//    }
//
//    public void debug(Object object, Throwable e) {
//        logger.debug(object, e);
//    }
//
//    public void info(Object object) {
//        logger.info(object);
//    }
//
//    public void info(Object object, Throwable e) {
//        logger.info(object, e);
//    }
//
//    public void warn(Object object) {
//        logger.warn(object);
//    }
//
//    public void warn(Object object, Throwable e) {
//        logger.warn(object, e);
//    }
//
//    public void error(Object object) {
//        logger.error(object);
//    }
//
//    public void error(Object object, Throwable e) {
//        logger.error(object, e);
//    }
//
//    public void fatal(Object object) {
//        logger.fatal(object);
//    }
//
//    public String getName() {
//        return logger.getName();
//    }
//
//    public org.apache.log4j.Logger getLog4jLogger() {
//        return logger;
//    }
//
//    public boolean equals(Logger newLogger) {
//        return logger.equals(newLogger.getLog4jLogger());
//    }
//
//    public static void logBefore(Logger logger, String interfaceName){
//        logger.info("");
//        logger.info("start");
//        logger.info(interfaceName);
//    }
//
//    public static void logAfter(Logger logger){
//        logger.info("end");
//        logger.info("");
//    }
//
//}
