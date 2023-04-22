package com.tencent.wxcloudrun.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author : chenzg
 * @date : 2022-07-15 14:06:30
 **/
public class JsonUtil {
    public static JSONObject parseJson(String json) {
        return json == null ? new JSONObject() : JSONObject.parseObject(json);
    }

    public static JSONArray parseJsonArray(String json) {
        return json == null ? new JSONArray() : JSONArray.parseArray(json);
    }

    public static String toJson(Object json) {
        return JSONObject.toJSONString(json);
    }

    public static void main(String[] args) {
        System.out.println(toJson("[1,2]"));
    }
}
