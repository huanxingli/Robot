package com.example.administrator.robot.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.robot.MainActivity;
import com.example.administrator.robot.R;
import com.example.administrator.robot.utils.SharedPreferencesHelper;
import com.example.administrator.robot.utils.UiHelp;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by Administrator on 2016/3/17.
 */
public class RegisterActivity extends Activity {

    private EditText editPhone;
    private Button code;
    private Button register;
    private EditText editCode;
    private EditText editUser;
    private EditText editPassword;
    private EditText editCfPassword;


    private int time = 0;

    private Timer timer = null;
    private TimerTask timerTask = null;

    private static final String ID = "3ad457b3879e3ad3b1e772cd974f8cc8";

    private Handler handler;
    private MyThread myThread;

    private Handler secHandler;
    private MySecThread mySecThread;

    class MySecThread extends Thread{
        @Override
        public void run() {
            Looper.prepare();
            secHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    BmobUser bu = (BmobUser) msg.obj;
                    bu.signUp(RegisterActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setTitle("注册消息框");
                            builder.setMessage("恭喜，注册成功！");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                            builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            };
            Looper.loop();
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x123) {
                        String phone = String.valueOf(editPhone.getText());
                        BmobSMS.requestSMSCode(RegisterActivity.this, phone, "bmobTest", new RequestSMSCodeListener() {
                            @Override
                            public void done(Integer integer, BmobException e) {
                                if (e == null) {
                                    Log.i("短信", "短信发送成功");
                                }
                            }
                        });
                    }
                }
            };
            Looper.loop();
        }
    }


    //修改获取验证码按钮UI
    public Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            code.setText("还剩下" + msg.arg1 + "秒");
            code.setBackgroundResource(R.drawable.codepress_btn);
            code.setTextColor(Color.WHITE);
            if (msg.arg1 == 0) {
                stopTime();
                code.setEnabled(true);
                code.setBackgroundResource(R.drawable.code_btn);
                code.setTextColor(Color.GRAY);
                code.setText("获取验证码");
            }
        }
    };


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            initView();
            setListener();
        }

        private void setListener() {
            //发送验证码
            code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(0x123);
                    //一分钟倒计时
                    code.requestFocus();
                    time = 60;
                    startTime();
                    code.setEnabled(false);
                }
            });

            //注册
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = String.valueOf(editPhone.getText());
                    String code = String.valueOf(editCode.getText());
                    BmobSMS.verifySmsCode(RegisterActivity.this, phone, code, new VerifySMSCodeListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                String name = String.valueOf(editUser.getText());
                                String password = String.valueOf(editPassword.getText());
                                String cfPassword = String.valueOf(editCfPassword.getText());
                                if (!password.equals(cfPassword)){
                                    UiHelp.getInstance().showText(RegisterActivity.this,
                                            "密码和确认密码不一致");
                                    return;
                                }
                                BmobUser bu = new BmobUser();
                                bu.setUsername(name);
                                bu.setPassword(password);
                                Message message = new Message();
                                message.obj = bu;
                                secHandler.sendMessage(message);

                            } else {
                                Log.i("smile", "验证失败：code =" + e.getErrorCode() + ",msg = " + e.getLocalizedMessage());
                            }
                        }
                    });
                }
            });
        }

        private void startTime() {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    time--;
                    Message message = Message.obtain();
                    message.what = 0x456;
                    message.arg1 = time;
                    handler2.sendMessage(message);
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }

        private void stopTime() {
            timer.cancel();
        }

        private void initView() {
            Bmob.initialize(this, ID);
            editPhone = (EditText) findViewById(R.id.register_phone);
            code = (Button) findViewById(R.id.register_btn_code);
            register = (Button) findViewById(R.id.register_btn_sure);
            editCode = (EditText) findViewById(R.id.phone_code);
            editUser = (EditText) findViewById(R.id.register_user);
            editPassword = (EditText) findViewById(R.id.register_password);
            editCfPassword = (EditText) findViewById(R.id.register_surepsd);
            myThread = new MyThread();
            mySecThread = new MySecThread();
            myThread.start();
            mySecThread.start();
        }
}
