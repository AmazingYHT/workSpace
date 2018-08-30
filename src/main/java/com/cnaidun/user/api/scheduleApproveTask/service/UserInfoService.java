package com.cnaidun.user.api.scheduleApproveTask.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.cnaidun.user.api.scheduleApproveTask.bean.IdInfo;
import com.cnaidun.user.api.scheduleApproveTask.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UserInfoService {

   @Autowired
    private UserInfoMapper userInfoMapper;

    public List<IdInfo> authentication(IdInfo info) {
        List<IdInfo> list =userInfoMapper.selectAll();

        return list;
    }


}
