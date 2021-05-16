package com.iams.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static long dateDiff(Date verifyTime) throws Exception {
        return dateDiff(verifyTime,new Date());
    }

    public static long dateDiff(String verifyTime, String registerTime) throws Exception {
        return dateDiff(verifyTime,registerTime,"yyyy-MM-dd HH:mm:ss");
    }

    public static long dateDiff(String verifyTime, String registerTime, String format) throws Exception {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        Date verify = sd.parse(verifyTime);
        Date register = sd.parse(registerTime);
        return dateDiff(register,verify);
    }

    /***
     * 计算时间差
     * @param verifyTime 验证时间
     * @param registerTime 注册时间
     * @return
     * @throws Exception
     */
    public static long dateDiff(Date verifyTime, Date registerTime) {
        long verify = verifyTime.getTime();//验证时间
        long register = registerTime.getTime();//注册时间
        return ((register - verify) / (1000 * 60));
    }


}
