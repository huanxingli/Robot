package com.example.administrator.robot.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/3/21.
 */
public class UiHelp {
    private volatile static UiHelp instance;

    private UiHelp(){}

    public static UiHelp getInstance(){
        if (instance==null){
            synchronized (UiHelp.class){
                if (instance==null){
                    instance = new UiHelp();
                }
            }
        }
        return instance;
    }

    public void showText(Context context,String info){
        Toast.makeText(context,info,Toast.LENGTH_LONG).show();
    }
}
