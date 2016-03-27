package com.example.administrator.robot.utils;

import android.util.Log;

import com.example.administrator.robot.bean.ChatMessage;
import com.example.administrator.robot.bean.FoodList;
import com.example.administrator.robot.bean.FoodResult;
import com.example.administrator.robot.bean.NewList;
import com.example.administrator.robot.bean.NewsResult;
import com.example.administrator.robot.bean.TextResult;
import com.example.administrator.robot.bean.UrlResult;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 */
public class HttpUtils {

    private static final String URL= "http://www.tuling123.com/openapi/api";
    private static final String KEY = "a4ebb4e945e7c5e395e8f1297773ae46";

    /**
     * 将获取的json数据封装到ChatMessage中去
     * @param info
     * @return
     */
    public static ChatMessage getChatMessage(String info){
        ChatMessage chatMessage = new ChatMessage();
        TextResult textResult = null;
        UrlResult urlResult = null;
        NewsResult newsResult = null;
        FoodResult foodResult = null;
        try {
            int code = GsonHelp.parseInt("code",info);
            switch (code){
                case 100000:
                    textResult = GsonHelp.parseObject(info,TextResult.class);
                    chatMessage.setMsg(textResult.getText());
                    chatMessage.setType(ChatMessage.Type.INCOMING);
                    break;
                case 200000:
                    urlResult = GsonHelp.parseObject(info,UrlResult.class);
                    chatMessage.setMsg(urlResult.getText() + urlResult.getUrl());
                    chatMessage.setType(ChatMessage.Type.INCOMING);
                    break;
                case 302000:
                    newsResult = GsonHelp.parseObject(info,NewsResult.class);
                    chatMessage.setMsg(newsResult.getText() + ":\n" + setArticles(newsResult.getmList()));
                    chatMessage.setType(ChatMessage.Type.INCOMING);
                    break;
                case 308000:
                    foodResult = GsonHelp.parseObject(info,FoodResult.class);
                    chatMessage.setMsg(foodResult.getText() + ":\n" + setFoods(foodResult.getList()));
                    chatMessage.setIconUrl(foodResult.getList().get(0).getIcon());
                    chatMessage.setType(ChatMessage.Type.IMG);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            chatMessage.setMsg("服务器繁忙，请稍后再试！");
        }
        chatMessage.setDate(new Date());
        return chatMessage;
    }

    public static String setArticles(List<NewList> lists){
        String info = "";
        for (NewList newList:lists){
            info += "标题：" + newList.getArticle()  + "\n来源：" +
                    newList.getSource() + "\n链接：" + newList.getDetailurl() + "\n";
        }
        return info;
    }

    public static String setFoods(List<FoodList> foodList){
        String info = "";
        info = info + "菜名：" + foodList.get(0).getName() + "\n材料：" + foodList.get(0).getInfo() +
                "\n详细链接地址：" + foodList.get(0).getDetailurl() ;
        return info;
    }

//    public void doPost(String msg){
//        String url = setUrlParams(msg);
//        ApiClient.init();
//        ApiClient.postUrl(url,null,new AsyncHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                String info = null;
//                info = new String(responseBody);
//                Log.i("TAG",info);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                super.onFailure(statusCode, headers, responseBody, error);
//            }
//        });
//    }

    public static String setUrlParams(String msg){
        String result = "";
        result = URL + "?key=" + KEY + "&info=" + msg + "&userid=123456";
        return result;
    }
}
