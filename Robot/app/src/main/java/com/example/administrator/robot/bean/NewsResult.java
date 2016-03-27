package com.example.administrator.robot.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/15.
 */
public class NewsResult {
    private int code;
    private String text;
    private List<NewList> list;

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

    public List<NewList> getmList() {
        return list;
    }

    public void setmList(List<NewList> list) {
        this.list = list;
    }
}
