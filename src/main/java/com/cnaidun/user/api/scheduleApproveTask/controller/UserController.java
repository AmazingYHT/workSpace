package com.cnaidun.user.api.scheduleApproveTask.controller;

import com.alibaba.fastjson.JSONObject;
//import com.cnaidun.user.api.scheduleApproveTask.service.AsyncWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import com.cnaidun.user.api.policeUser.service.ActivityConsumerService;
//import com.cnaidun.user.api.policeUser.service.ActivityConsumerService;
//import io.swagger.annotations.ApiOperation;

/**
 * 项目名称：PoliceCloud
 * 类名称：WorkSheetController
 * 类描述：
 * 创建人：yf
 * 创建时间：2018/7/31
 * 修改人：yf
 * 修改时间：2018/7/31
 * 修改备注：
 * 版权所有权：江苏艾盾网络科技有限公司
 *
 * @version V1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
//
//    @Autowired
//    private AsyncWxService asyncWxService;
//
//    @RequestMapping("/test")
//    public String submit(){
//        logger.info("start submit");
//
//        //调用service层的任务
//        asyncWxService.executeAsync();
//
//        logger.info("end submit");
//
//        return "success";
//    }
}

