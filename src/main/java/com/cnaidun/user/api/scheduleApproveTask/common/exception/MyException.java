package com.cnaidun.user.api.scheduleApproveTask.common.exception;

/**
 * 项目名称：PoliceCloud
 * 类名称：MyException
 * 类描述：自定义异常
 * 创建人：JackJun
 * 创建时间：2018/7/25
 * 修改人：JackJun
 * 修改时间：2018/7/25
 * 修改备注：
 * 版权所有权：江苏艾盾网络科技有限公司
 *
 * @version V1.0
 */
public class MyException extends RuntimeException {
    private static final long serialVersionUID = -13345478421L;
    public MyException() {
        super();
    }
    public MyException(String msg) {
        super(msg);
    }
    public MyException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public MyException(Throwable cause) {
        super(cause);
    }

}
