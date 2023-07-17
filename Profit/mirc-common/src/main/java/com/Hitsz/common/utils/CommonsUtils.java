package com.Hitsz.common.utils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class CommonsUtils {

    /**
     * 手机号脱敏处理
     * @param phone 手机号
     * @return 脱敏后的手机号
     */
    public static String makePhoneNotEasyTolook(String phone){
        String result = "***********";
        if(phone != null && phone.length() == 11){
            result = phone.substring(0,3) + "******" + phone.substring(9,11);
        }
        return result;
    }

    public static boolean judgePhoneIsCorrect(String phone){

        boolean correct = false;
        if(phone != null && phone.length() == 11){
            correct = Pattern.matches("^1[1-9]\\d{9}$",phone);
        }
        return correct;
    }

    public static boolean CompareNums(BigDecimal b1,BigDecimal b2){
        if(b1 == null || b2 == null){
            throw new RuntimeException("无法比较两个数的大小");
        }
        return b1.compareTo(b2) >= 0;
    }
}
