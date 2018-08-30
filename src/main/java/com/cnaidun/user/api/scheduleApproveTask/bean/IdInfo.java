package com.cnaidun.user.api.scheduleApproveTask.bean;

import lombok.Data;

import java.util.Date;

/**
 * Copyright (C), 2018-2018
 * FileName: WorkSheet
 * Author:   cjle
 * Date:     2018/7/11 13:37
 * Description: 用户表
 * History:
 * <author>          <time>          <version>          <desc>
 * 修改者           修改时间           版本号              描述
 */
@Data
public class IdInfo {
    /**
     * 用户id
     */
    private String uid;
    /**
     *身份证号
     */
    private String idno;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;

    /**
     * 民族
     */
    private String nation;
    /**
     * 城市编码
     */
    private String citycode;

    /**
     * 地址
     */
    private String address;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 电话
     */
    private String phone;
    /**
     * 身份证有效日期
     */
    private String validdate;
    /**
     * 授权身份证单位
     */
    private String authority;
    /**
     * 创建时间
     */
    private Date createtime;

    private String idpositive;

    private String idopposite;

    private String openid;



    public IdInfo() {
    }

    public IdInfo(String idno, String uid, String name, String sex, String citycode, String address, String phone) {
        this.idno = idno;
        this.uid = uid;
        this.name = name;
        this.sex = sex;
        this.nation = nation;
        this.citycode = citycode;
        this.address = address;
        this.phone = phone;
    }


}
