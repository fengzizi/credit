package com.credit.diversion.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class RandomUtil {

    /**
     * 获得不大于32位随机字符串
     * @return
     */
    public static String getNonceStr(){
        Random random = new Random();
        long val = random.nextLong();
        String res = DigestUtils.md5Hex(val+"tugou").toUpperCase();
        if(32<res.length()) return res.substring(0,32);
        else return res;
    }
}
