package com.cnaidun.user.api.scheduleApproveTask.common.validator;

import com.cnaidun.user.api.scheduleApproveTask.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * 项目名称：PoliceCloud
 * 类名称：Assert
 * 类描述 类Assert的功能描述:
 * 数据校验
 * 创建人：JackJun
 * 创建时间：2018/7/25
 * 修改人：JackJun
 * 修改时间：2018/7/25
 * 修改备注：
 * 版权所有权：江苏艾盾网络科技有限公司
 *
 * @version V1.0
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
