package com.example.administrator.robot.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2016/3/26.
 */
public class SharedPreferencesHelper {

    public static void putUserName(Context context,String value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserName",value);
        editor.commit();
    }

    public static String getUserName(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("UserName","");
    }
}
