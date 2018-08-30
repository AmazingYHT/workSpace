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
public class WeiXinDataInterChange {
    /**
     * 用户状态
     */
    private String status;
    /**
     *身份证号
     */
    private String uid;
    /**
     *手机号
     */
    private String workno;

    private String openid;

    private String name;

    private String worktypecode;

    private String workname;

    private Date createtime;

   private String docNO;

   private String opinion;

    private  String phone;


    public WeiXinDataInterChange() {
    }

    public WeiXinDataInterChange(String idno, String uid, String mobile, Date startday, Date endday, int wsend, int dsend) {

    //    this.phone = phone;
    }


}
