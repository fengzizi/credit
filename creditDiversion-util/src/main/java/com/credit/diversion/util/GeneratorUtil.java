package com.credit.diversion.util;

import java.util.UUID;

public class GeneratorUtil {

    /**
     * 获取11位唯一编号
     *
     * @return
     */
    public static String getUniqueNo() {
        int hashCode = UUID.randomUUID().toString().hashCode();
        hashCode = hashCode < 0 ? -hashCode : hashCode;
        return String.format("%011d", hashCode);
    }
}
