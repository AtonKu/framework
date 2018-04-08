package com.atonku.service;

import com.atonku.entity.Customer;
import com.atonku.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * 标准MVC 架构中是没有服务层的, 我们将该层作为衔接控制层和数据库之间的桥梁
 * 可以使用接口和实现类的方式来表达, 应根据具体情况来使用, 并非必须使用接口
 *
 * There is no service layer in standard MVC architecture.
 * We regard this layer as a bridge between cohesion control layer and database.
 * It can be expressed in terms of interfaces and implementation classes,
 * and should be used according to specific situations, rather than using interfaces.
 *
 * @Date: 2018/3/30 16:18
 * @Author: GYT
 * @Modified by:
 *
 **/
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    /**
     * 在该类中执行数据库操作。
     */

    /*为jdbc配置项定义一些常量，并提供一个静态代码块来初始化这些常量*/
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties conf = PropsUtil.loadProps("jdbc.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    /**
     * 获取客户列表
     * @param keyword
     * @return
     * @notes: PreparedStatement是java.sql包下面的一个接口，用来执行SQL语句查询，
     *         通过调用connection.preparedStatement(sql)方法可以获得PreparedStatment对象。
     *         数据库系统会对sql语句进行预编译处理（如果JDBC驱动支持的话），预处理语句将被预先编译好，
     *         这条预编译的sql查询语句能在将来的查询中重用，这样一来，它比Statement对象生成的查询速度更快。
     *         PreparedStatement可以写动态参数化的查询;
     *         PreparedStatement比 Statement 更快;
     *         PreparedStatement可以防止SQL注入式攻击（拼接sql字符串的形式进行数据库攻击）
     *         PreparedStatement不允许一个占位符（？）有多个值，在执行有**IN**子句查询的时候这个问题变得棘手起来。
     */
    public List<Customer> getCustomerList(String keyword){
        //TODO
        Connection connection = null;
        try {
            List<Customer> customerList = new ArrayList<Customer>();
            String sql = "SELECT * FROM customer";
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setContact(resultSet.getString("contact"));
                customer.setRemark(resultSet.getString("remark"));
                customer.setTelephone(resultSet.getString("telephone"));
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            LOGGER.error("execute sql failure", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("close connection failure", e);
                }
            }
        }

        return null;
    }

    /**
     * 获取客户详情
     * @param id
     * @return
     */
    public Customer getCustomer(long id){
        //TODO
        return null;
    }

    /**
     * 创建用户
     * @param fieldMap
     * @return
     */
    public boolean creatCustomer(Map<String, Object> fieldMap){
        // TODO
        return false;
    }

    /**
     * 更新客户
     * @param id
     * @param fieldMap
     * @return
     */
    public boolean updateCusomer(long id, Map<String, Object> fieldMap){
        //TODO
        return false;
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    public boolean deleteCustomer(long id){
        //TODO
        return false;
    }

}
