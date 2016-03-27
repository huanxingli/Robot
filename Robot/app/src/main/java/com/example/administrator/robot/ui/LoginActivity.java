package com.example.administrator.robot.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.robot.MainActivity;
import com.example.administrator.robot.R;
import com.example.administrator.robot.utils.SharedPreferencesHelper;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/3/25.
 */
public class LoginActivity extends Activity  {

    private EditText editName;
    private EditText editPassword;
    private Button btnLogin;
    private TextView textRegister;

    private Handler handler;
    private MyThread2 myThread;

    class MyThread2 extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    final BmobUser bu = (BmobUser)msg.obj;
                    bu.login(LoginActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            SharedPreferencesHelper.putUserName(LoginActivity.this,bu.getUsername());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("登录情况");
                            builder.setMessage("登录不成功，请重新输入！");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                        }
                    });
                }

            };
            Looper.loop();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(editName.getText());
                String password = String.valueOf(editPassword.getText());
                BmobUser bu = new BmobUser();
                bu.setUsername(name);
                bu.setPassword(password);
                Message message = Message.obtain();
                message.obj = bu;
                handler.sendMessage(message);
            }
        });
    }

    private void initView() {
        editName = (EditText) findViewById(R.id.edit_user);
        editPassword = (EditText) findViewById(R.id.edit_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        textRegister = (TextView) findViewById(R.id.login_register);
        myThread = new MyThread2();
        myThread.start();
    }


}
