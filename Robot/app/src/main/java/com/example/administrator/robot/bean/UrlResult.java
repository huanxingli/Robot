package com.example.administrator.robot.bean;

/**
 * Created by Administrator on 2016/3/14.
 */
public class UrlResult {
    private int code;
    private String text;
    private String url;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
