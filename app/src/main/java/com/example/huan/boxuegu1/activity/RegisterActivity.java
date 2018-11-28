package com.example.huan.boxuegu1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huan.boxuegu1.utils.MD5Utils;
import com.example.huan.boxuegu1.R;

public class RegisterActivity extends AppCompatActivity {
    private TextView tv_main_title,tv_back;//标题和返回按钮
    private Button btn_register;//注册按钮

    private EditText et_user_name,et_psw,et_psw_again;//用户名、密码、再次输入密码的控件
    private String userName,psw,pswAgain;//用户名、密码、再次输入密码的控件的获取值

    private RelativeLayout rl_title_bar;//顶部的标题布局栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);//设置页面布局
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置页面布局为竖屏
        init();
    }

    private void init(){//完成界面的初始化以及相关的业务逻辑
        tv_main_title=(TextView)findViewById(R.id.tv_main_title);//从mian_title_bar.xml页面中获取对应的UI控件
        tv_main_title.setText("注册");

        tv_back=(TextView)findViewById(R.id.tv_back);
        rl_title_bar=(RelativeLayout)findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);//从activity_register.xml页面布局中获得相应的UI控件
        btn_register=(Button)findViewById(R.id.btn_register);
        et_user_name=(EditText) findViewById(R.id.et_user_name);
        et_psw=(EditText)findViewById(R.id.et_psw);
        et_psw_again=(EditText)findViewById(R.id.et_psw_again);

        tv_back.setOnClickListener(new View.OnClickListener() {//鼠标监听事件，当按下返回事，关闭当前页面
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();//关闭当前页面
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击注册按钮响应事件
                getEditString();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!psw.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this,"两次输入的密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }else if(isExitUserName(userName)){
                    Toast.makeText(RegisterActivity.this,"此用户名已经存在",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    saveRegisterInfo(userName,psw);//把用户名和密码保存到SharedPreferences中
                    Intent data=new Intent();//注册成功后把用户名传递到LoginActivity.java中
                    data.putExtra("userName",userName);
                    setResult(RESULT_OK,data);
                    RegisterActivity.this.finish();
                }
            }
        });
    }

    private void getEditString(){//用于获取注册 界面输入的内容
        userName=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();
    }

    private boolean isExitUserName(String userName){//从SharedPreferences中读取输入的用户名，判断用户名是否已经存在
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("logininfo",MODE_PRIVATE);
        String spPsw=sp.getString(userName,"");//？
        if(!TextUtils.isEmpty(spPsw)){
            has_userName=true;
        }
        return has_userName;
    }

    private void saveRegisterInfo(String userName,String psw){
        String md5Psw= MD5Utils.md5(psw);//把密码用MD5加密
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);//loginInfo表示文件名
        SharedPreferences.Editor editor=sp.edit();//获取编辑器
        editor.putString(userName,md5Psw);//以用户名为key，密码为value保存到SharedPreferences
        editor.commit();//提交修改
    }
}
