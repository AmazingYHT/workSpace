package com.cnaidun.user.api.scheduleApproveTask.common.utils;



import com.cnaidun.user.api.scheduleApproveTask.common.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：PoliceCloud
 * 类名称：Result
 * 类描述：类Result的功能描述:
 * 返回数据实体类
 * 创建人：JackJun
 * 创建时间：2018/7/25
 * 修改人：JackJun
 * 修改时间：2018/7/25
 * 修改备注：
 * 版权所有权：江苏艾盾网络科技有限公司
 *
 * @version V1.0
 */
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public Result() {
        put("code", "0");
    }

    public Result(String code,String msg) {
        put("code", code);
        put("msg", msg);
    }

    public static Result error() {
        return new Result(Constant.RESULT.CODE_NO.getValue(), Constant.RESULT.MSG_NO.getValue());
    }

    public static Result error(String msg) {
        return error(Constant.RESULT.CODE_NO.getValue(), msg);
    }

    public static Result error(String code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result(Constant.RESULT.CODE_YES.getValue(),Constant.RESULT.MSG_YES.getValue());
    }

    public static Result success(Result data) {
        return data;
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
