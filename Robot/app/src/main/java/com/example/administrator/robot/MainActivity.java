package com.example.administrator.robot;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.robot.api.ApiClient;
import com.example.administrator.robot.bean.ChatMessage;
import com.example.administrator.robot.utils.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button btnSend;
    private EditText editMsg;

    private ListView mLists;
    private MyAdapter adapter;
    private List<ChatMessage> listMessages;

    private static final String ID = "3ad457b3879e3ad3b1e772cd974f8cc8";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ChatMessage chatMessage = (ChatMessage)msg.obj;
            listMessages.add(chatMessage);
            adapter.notifyDataSetChanged();
            mLists.setSelection(listMessages.size()-1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = (Button) findViewById(R.id.main_send);
        editMsg = (EditText)findViewById(R.id.main_edit);
        mLists = (ListView) findViewById(R.id.main_msg_list);
        initDatas();
        ApiClient.init();
        Bmob.initialize(this, ID);
        btnSend.setOnClickListener(this);
    }

    private void initDatas() {

        listMessages = new ArrayList<>();
        ChatMessage message1 = new ChatMessage("欢迎来到图灵机器人", ChatMessage.Type.INCOMING,new Date());
        listMessages.add(message1);

        adapter = new MyAdapter(this,listMessages);
        mLists.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_send:
                String msg = editMsg.getText().toString();
                if (msg==""){
                    Toast.makeText(MainActivity.this,"发送的信息不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                ChatMessage chatMessage = new ChatMessage(msg, ChatMessage.Type.OUTCOMING,new Date());
                Message m = Message.obtain();
                m.obj = chatMessage;
                handler.sendMessage(m);

                //获取返回的信息
                String url = HttpUtils.setUrlParams(msg);
                ApiClient.postUrl(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String info = null;
                        info = new String(responseBody);
                        Log.i("TAG",info);
                        ChatMessage chatMessage = HttpUtils.getChatMessage(info);
                        Message m = new Message();
                        m.obj = chatMessage;
                        handler.sendMessage(m);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        super.onFailure(statusCode, headers, responseBody, error);
                    }
                });
                editMsg.setText("");

                break;
        }
    }
}
