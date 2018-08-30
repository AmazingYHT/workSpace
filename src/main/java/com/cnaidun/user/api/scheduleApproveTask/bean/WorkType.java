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
public class WorkType {

    /**
     *  业务编码
     */
    private String worktypecode;
    /**
     *业务级别
     */
    private String worklevel;
    /**
     *业务名称
     */
    private String workname;



}
