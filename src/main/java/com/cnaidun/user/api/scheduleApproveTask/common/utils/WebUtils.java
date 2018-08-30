package com.cnaidun.user.api.scheduleApproveTask.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 项目名称：PoliceCloud
 * 类名称：WebUtils
 * 类描述：
 * 创建人：JackJun
 * 创建时间：2018/7/25
 * 修改人：JackJun
 * 修改时间：2018/7/25
 * 修改备注：
 * 版权所有权：江苏艾盾网络科技有限公司
 *
 * @version V1.0
 */
public class WebUtils {
    /**
     * 判断请求是否是ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        String accept = request.getHeader("accept");
        if(accept != null && accept.indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)){
            return true;
        }
        return false;
    }
}
