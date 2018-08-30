package com.cnaidun.user.api.scheduleApproveTask.common.utils;

import java.security.MessageDigest;

/**
 * 项目名称：PoliceCloud
 * 类名称：MD5
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
public class MD5 {
    private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String byteArrayToHexString(byte[] iI1l) {
        StringBuffer illl1111I1I = new StringBuffer();
        for (int i11I = 0; i11I < iI1l.length; ++i11I)
            illl1111I1I.append(byteToHexString(iI1l[i11I]));

        return illl1111I1I.toString();
    }

    private static String byteToHexString(byte iI1l) {
        int ilIl = iI1l;
        if (ilIl < 0)
            ilIl += 256;
        int iIII1 = ilIl / 16;
        int i1I1I = ilIl % 16;
        return hexDigits[iIII1] + hexDigits[i1I1I];
    }

    public static String MD5Encode(String iIIllI1II) {
        String i1I1lIll1Ill1I1 = null;
        try {
            i1I1lIll1Ill1I1 = new String(iIIllI1II);
            MessageDigest iIl1l = MessageDigest.getInstance("MD5");
            i1I1lIll1Ill1I1 = byteArrayToHexString(iIl1l.digest(i1I1lIll1Ill1I1.getBytes()));
        } catch (Exception iIl1l) {
        }
        return i1I1lIll1Ill1I1;
    }
}
