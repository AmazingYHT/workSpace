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
public class WxSend {

    /**
     *身份证号
     */
    private String idno;
    /**
     *uuid
     */
    private String wid;
    /**
     *手机号
     */
    private String mobile;
    /**
     *手机号
     */
    private String endTime;
    /**
     * 结束时间
     */
    private Date endday;
    /**
     * 微信id
     */
    private String openid;
    /**
     * 创建时间
     */
    private Date createtime;



    public WxSend() {
    }

    public WxSend(String idno, String endTime, String mobile, String openid, Date endday) {
        this.idno = idno;
        this.endTime = endTime;
        this.mobile = mobile;
        this.openid = openid;
        this.endday = endday;

    //    this.phone = phone;
    }


}
