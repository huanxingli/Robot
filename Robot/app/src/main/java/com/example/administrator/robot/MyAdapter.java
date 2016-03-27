package com.example.administrator.robot;

import android.content.Context;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.robot.bean.ChatMessage;
import com.example.administrator.robot.utils.SharedPreferencesHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/3/15.
 */
public class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ChatMessage> lists;
    private Context context;

    public MyAdapter(Context context, List<ChatMessage> lists) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = lists.get(position);
        //判断是接收消息 or 发送消息
        if(message.getType()== ChatMessage.Type.INCOMING){
            return 0;
        }else if (message.getType()==ChatMessage.Type.IMG){
            return 1;
        }else{
            return 2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatMessage message = lists.get(position);
        ViewHold viewHold = null;
        if (convertView == null){
            if (getItemViewType(position)==0){ //是接收消息的布局
                convertView = inflater.inflate(R.layout.item_from_msg,parent,false);
                viewHold = new ViewHold();
                viewHold.mDate = (TextView) convertView.findViewById(R.id.item_from_date);
                viewHold.mMsg = (TextView) convertView.findViewById(R.id.item_from_msg);
            }else if (getItemViewType(position)==2){  //发送消息的布局
                convertView = inflater.inflate(R.layout.item_to_msg,parent,false);
                viewHold = new ViewHold();
                viewHold.mDate = (TextView) convertView.findViewById(R.id.item_to_date);
                viewHold.mMsg = (TextView) convertView.findViewById(R.id.item_to_msg);
                viewHold.mUserName = (TextView) convertView.findViewById(R.id.item_username);
            }else{ //带有图片的
                convertView = inflater.inflate(R.layout.item_from_img,parent,false);
                viewHold = new ViewHold();
                viewHold.mDate = (TextView) convertView.findViewById(R.id.item_img_date);
                viewHold.mMsg = (TextView) convertView.findViewById(R.id.item_Img_msg);
                viewHold.mImg = (ImageView) convertView.findViewById(R.id.item_img_src);
            }
            convertView.setTag(viewHold);
        }else{
            viewHold = (ViewHold) convertView.getTag();
        }
        //设置数据
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        viewHold.mDate.setText(simpleDateFormat.format(message.getDate()));
        viewHold.mMsg.setText(message.getMsg());
        if (getItemViewType(position)== 1)
            Picasso.with(context).load(message.getIconUrl()).into(viewHold.mImg);
        if (getItemViewType(position)==2)
            viewHold.mUserName.setText(SharedPreferencesHelper.getUserName(context));
        return convertView;
    }

    private final class ViewHold{
        TextView mDate;
        TextView mMsg;
        ImageView mImg;
        TextView mUserName;
    }
}
