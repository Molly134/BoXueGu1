package com.example.huan.boxuegu1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huan.boxuegu1.R;

public class SettingActivity extends AppCompatActivity {

    private TextView tv_main_title;
    private TextView tv_back;
    private RelativeLayout rl_title_bar;
    private RelativeLayout rl_modify_psw,rl_security_setting,rl_exit_login;
    public static SettingActivity instance=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
        instance=this;
        init();
    }

    private void init(){//获取界面控件
        tv_main_title=(TextView)findViewById(R.id.tv_main_title);
        tv_main_title.setText("设置");
        tv_back=(TextView)findViewById(R.id.tv_back);
        rl_title_bar=(RelativeLayout)findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30b4ff"));
        rl_modify_psw=(RelativeLayout)findViewById(R.id.rl_modify_psw);
        rl_security_setting=(RelativeLayout)findViewById(R.id.rl_security_setting);
        rl_exit_login=(RelativeLayout)findViewById(R.id.rl_exit_login);

        tv_back.setOnClickListener(new View.OnClickListener() {//返回按钮点击事件
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });

        rl_modify_psw.setOnClickListener(new View.OnClickListener() {//修改密码点击事件
            @Override
            public void onClick(View v) {//修改密码的点击事件
                Intent intent=new Intent(SettingActivity.this,ModifyPswActivity.class);//跳转到修改密码界面
                startActivity(intent);
            }
        });

        rl_security_setting.setOnClickListener(new View.OnClickListener() {//设置密保点击事件
            @Override
            public void onClick(View v) {
                //跳转到设置密保界面
                Intent intent=new Intent(SettingActivity.this,FindPswActivity.class);
                intent.putExtra("from","security");
                startActivity(intent);
            }
        });

        rl_exit_login.setOnClickListener(new View.OnClickListener() {//退出登录点击事件
            @Override
            public void onClick(View v) {//退出登录的点击事件
                Toast.makeText(SettingActivity.this,"退出登录成功",Toast.LENGTH_SHORT).show();
                clearLoginStatus();//清除登录状态和登录时用户名
                //退出登录成功后把退出成功的状态传递到Mainactivity中
                Intent data=new Intent();
                data.putExtra("isLogin",false);
                setResult(RESULT_OK,data);
                SettingActivity.this.finish();
            }
        });
    }

    private void clearLoginStatus(){// 清除登录状态和登录时用户名
        SharedPreferences sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();// 获取编辑器
        editor.putBoolean("islogin",false);// 清除登录状态
        editor.putString("loginUserName","");// 清除用户名
        editor.commit();// 提交修改
    }
}
