package com.atonku.service;

import com.atonku.entity.Customer;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取客户列表
     * @param keyword
     * @return
     */
    public List<Customer> getCustomerList(String keyword){
        //TODO
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
