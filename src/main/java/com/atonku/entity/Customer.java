package com.atonku.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @Date: 2018/3/30 15:49
 * @Author: guyatong
 * @Modified by:
 **/
@Data
@Getter
@Setter
public class Customer {

    private long id;

    private String name;

    private String contact;

    private String telephone;

    private String email;

    private String remark;

//
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getContact() {
//        return contact;
//    }
//
//    public void setContact(String contact) {
//        this.contact = contact;
//    }
//
//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
}
