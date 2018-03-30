package com.atonku.controller2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 客户创建控制器, 使用相同的url地址处理不同的请求
 * 在servlet中有四中请求, 分别为POST, GET, DELTEE, PUT, 对应的请求为doPost, doGet, doDelete, doPut
 * @Date: 2018/3/30 16:13
 * @Author: guyatong
 * @Modified by:
 **/
@WebServlet("/customer_create")
public class CreataCustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO
    }
}
