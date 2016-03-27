package com.example.administrator.robot.utils;

import com.example.administrator.robot.bean.TextResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/14.
 */
public class GsonHelp {

    public static <T> T parseObject(String json,Class<T> clazz){
        Gson gson = new Gson();
        T tResult = null;
        try {
            tResult = gson.fromJson(json, TypeToken.get(clazz).getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return tResult;
    }

    public static int parseInt(String key,String json){
        JSONObject object;
        try {
            object = new JSONObject(json);
            if (object.has(key)){
                return object.getInt(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
