package com.credit.diversion.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtil {
    //Json序列化
    public static String toJson(Object obj) {
        if (obj == null) return null;
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    //json转实体
    public static <T> T fromJson(String json, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    //json转JsonElement
    public static JsonElement parse(String json) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(json);
    }
}
