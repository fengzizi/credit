package com.credit.diversion.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

public class ParseUtil {
    public static String toString(Object value, String defaultValue) {
        if (value == null) return defaultValue;
        return value.toString();
    }

    public static String toXMLString(Map<String, String> map) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("<xml>");
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            StringBuffer stringBuffer1 = new StringBuffer();
            stringBuffer1.append("<").
                    append(stringStringEntry.getKey()).
                    append(">").append("<![CDATA[").append(stringStringEntry.getValue()).append("]]>").
                    append("</").
                    append(stringStringEntry.getKey()).
                    append(">");
            list.add(stringBuffer1.toString());
        }
        Collections.sort(list);
        for (String s : list) {
            stringBuffer.append(s);
        }
        stringBuffer.append("</xml>");
        return stringBuffer.toString();
    }

    public static Map<String,String> xmlToMap(String src) throws DocumentException {
        Map<String,String> map = new HashMap<>();
        Document document = DocumentHelper.parseText(src);
        Element rootElement = document.getRootElement();
        Iterator<Element> iterator = rootElement.elementIterator();
        while(iterator.hasNext()){
            Element next = iterator.next();
            map.put(next.getName(),next.getData().toString());
        }
        return map;
    }

    public static int toInt(String value, int defaultValue) {
        if (value == null || value == "") return defaultValue;
        int data = 0;
        try {
            data = (int) (Float.parseFloat(value) / 1);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return data;
    }

    public static int toInt(Object value, int defaultValue) {
        return toInt(toString(value, ""), defaultValue);
    }

    public static long toLong(String value, long defaultValue) {
        if (value == null || value == "") return defaultValue;
        long data = 0;
        try {
            data = Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return data;
    }

    public static long toLong(Object value, long defaultValue) {
        return toLong(toString(value, ""), defaultValue);
    }

    public static boolean toBoolean(String value, boolean defaultValue) {
        if (value == null || value == "") return defaultValue;
        boolean data = false;
        try {
            data = Boolean.parseBoolean(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return data;
    }

    public static boolean toBoolean(Object value, boolean defaultValue) {
        return toBoolean(toString(value, ""), defaultValue);
    }
}
