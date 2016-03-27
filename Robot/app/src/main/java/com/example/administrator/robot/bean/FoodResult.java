package com.example.administrator.robot.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class FoodResult {
    private int code;
    private String text;
    private List<FoodList> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<FoodList> getList() {
        return list;
    }

    public void setList(List<FoodList> list) {
        this.list = list;
    }
}
