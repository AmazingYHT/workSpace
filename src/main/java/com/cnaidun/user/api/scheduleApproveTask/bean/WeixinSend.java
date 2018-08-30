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
public class WeixinSend {
    /**
     * 用户id(uuid)
     */
    private String wid;
    /**
     *身份证号
     */
    private String idno;
    /**
     *手机号
     */
    private String mobile;
    /**
     * 结束时间
     */
    private Date endday;
    /**
     * 开始时间
     */
    private Date startday;
    /**
     * 微信是否发送
     */
    private int wsend;
    /**
     * 短信是否发送
     */
    private int dsend;

    /**
     * 创建时间
     */
    private Date createtime;



    public WeixinSend() {
    }

    public WeixinSend(String idno, String uid, String mobile, Date startday, Date endday, int wsend, int dsend) {
        this.idno = idno;
        this.wid = wid;
        this.mobile = mobile;
        this.startday = startday;
        this.endday = endday;
        this.wsend = wsend;
        this.dsend = dsend;
    //    this.phone = phone;
    }


}
