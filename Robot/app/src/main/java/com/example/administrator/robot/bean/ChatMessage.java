package com.example.administrator.robot.bean;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/14.
 */
public class ChatMessage {
    private String name;
    private String msg;
    private Type type;
    private Date date;
    private String iconUrl;

    public enum Type
    {
        INCOMING, OUTCOMING ,IMG
    }

    public ChatMessage()
    {
    }

    public ChatMessage(String msg, Type type, Date date)
    {
        super();
        this.msg = msg;
        this.type = type;
        this.date = date;
    }



    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
