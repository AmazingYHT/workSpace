package com.cnaidun.user.api.scheduleApproveTask.service;


import com.alibaba.fastjson.JSONObject;

import com.cnaidun.user.api.scheduleApproveTask.bean.IdInfo;
import com.cnaidun.user.api.scheduleApproveTask.bean.WeiXinDataInterChange;
import com.cnaidun.user.api.scheduleApproveTask.bean.WorkType;
import com.cnaidun.user.api.scheduleApproveTask.bean.WxSend;
import com.cnaidun.user.api.scheduleApproveTask.mapper.UserInfoMapper;
import com.cnaidun.user.api.scheduleApproveTask.mapper.WeiXindataInterChangeMapper;
import com.cnaidun.user.api.scheduleApproveTask.mapper.WorkTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (C), 2018-2018
 * FileName: WorkSheetService
 * Author:   cjle
 * Date:     2018/7/11 13:37
 * Description: 交管模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 修改者           修改时间           版本号              描述
 */
@Service
public class WeiXindataInterChangeService {
    @Autowired
    private WeiXindataInterChangeMapper weiXindataInterChangeMapper;

    @Autowired
    private WorkTypeMapper workTypeMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;


    public List<WeiXinDataInterChange> selectUserInfo() {
       return weiXindataInterChangeMapper.selectUserInfo();
    }

    public List<WorkType> selectAll() {
        return workTypeMapper.selectAll();
    }

    public List<IdInfo> selectIdInfo() {
        return userInfoMapper.selectAll();
    }

    public void updateStatus(WeiXinDataInterChange data) {
         weiXindataInterChangeMapper.updateStatus(data);
    }


    // @Transactional
    //public int updateDriverContacts(WorkSheet workSheet){
    //    return workSheetMapper.updateDriverContacts(workSheet);
  //  }

}
