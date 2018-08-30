package com.cnaidun.user.api.scheduleApproveTask.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：PoliceCloud
 * 类名称：BaseController
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
@Slf4j
public class BaseController {

    /**
     * 成功的Status Code.
     */
    public static final int RESCODE_OK = 200;
    /**
     * 失败的Status Code.
     */
    public static final int RESCODE_FAIL = 201;

    /**
     * The action execution was a failure.
     */
    public static final String ERROR = "error/error";

    /**
     * 转到error/error页面时,model里面存储的错位信息的键
     */
    public static final String ERRORKEY = "errorMsg";
    /**
     * 其它未知异常
     */
    public static final String UNKOWNEXCEPTION = "请求失败";


    /**
     * 获取默认ajax成功信息.并传递参数到页面
     * @return
     */
    protected Map<String, Object> successResult(Object data) {
        return createJson(RESCODE_OK, "操作成功！", data);
    }

    /**
     * 获取默认ajax成功信息.
     * @return
     */
    protected Map<String, Object> successResult() {
        return createJson(RESCODE_OK, "操作成功！", Collections.EMPTY_MAP);
    }

    /**
     * 获取默认ajax成功信息.
     * @return
     */
    protected Map<String, Object> successResult(String msg) {
        return createJson(RESCODE_OK, msg, Collections.EMPTY_MAP);
    }


    /**
     * 获取失败信息.
     * @param msg
     * @return
     */
    protected Map<String, Object> failResult(String msg) {
        return createJson(RESCODE_FAIL, msg, Collections.EMPTY_MAP);
    }

    /**
     * 描述：组装json格式返回结果
     * @param resCode
     * @param errorMsg
     * @param obj
     * @return
     */
    protected Map<String, Object> createJson(int resCode, String errorMsg, Object obj) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rescode", resCode);
        jsonMap.put("msg", errorMsg);
        jsonMap.put("data", obj);
        return jsonMap;
    }

    /**
     *
     * @param totalCount
     * @param dataList
     * @return
     */
    protected Map<String, Object> dataTableJson(int totalCount, List<?> dataList) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("total", totalCount);
        data.put("content", dataList == null ? Collections.EMPTY_LIST : dataList);
        return data;
    }

    /**
     *
     * @param request
     * @param key
     * @param isNeed
     * @return
     */
    protected String getRequestParam(HttpServletRequest request, String key, boolean isNeed) {
        String result = StringUtils.defaultString(request.getParameter(key));
        if (isNeed && "".equals(result)) {
        }
        return result;
    }



}

