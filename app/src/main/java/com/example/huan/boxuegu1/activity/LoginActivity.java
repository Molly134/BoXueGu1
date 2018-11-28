package com.example.huan.boxuegu1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huan.boxuegu1.utils.MD5Utils;
import com.example.huan.boxuegu1.R;

public class LoginActivity extends AppCompatActivity {
    private TextView tv_register,tv_find_psw,tv_back;
    private Button btn_login;
    private EditText et_user_name,et_psw;

    private String userName,psw,spPsw;

    private TextView tv_mian_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init(){//控件初始化
        tv_register=(TextView)findViewById(R.id.tv_register);
        tv_find_psw=(TextView)findViewById(R.id.tv_find_psw);
        tv_back=(TextView)findViewById(R.id.tv_back);
        btn_login=(Button)findViewById(R.id.btn_login);

        et_user_name=(EditText)findViewById(R.id.et_user_name);
        et_psw=(EditText)findViewById(R.id.et_psw);

        tv_mian_title=(TextView)findViewById(R.id.tv_main_title);
        tv_mian_title.setText("登录");

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//返回按钮监听事件
                LoginActivity.this.finish();
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//注册按钮监听事件
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });

        tv_find_psw.setOnClickListener(new View.OnClickListener() {//找回密码监听点击事件
            @Override
            public void onClick(View v) {
                //跳转到找回密码界面
                Intent intent=new Intent(LoginActivity.this,FindPswActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {//登录按钮点击事件
            @Override
            public void onClick(View v) {
                userName=et_user_name.getText().toString().trim();
                psw=et_psw.getText().toString().trim();
                String md5Psw= MD5Utils.md5(psw);
                spPsw=readPsw(userName);
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(md5Psw.equals(spPsw)){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    saveLoginStatus(true,userName);//保存登录状态和用户名
                    Intent data=new Intent();//将登录成功状态传递到MainActivity中
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    LoginActivity.this.finish();
                    return;
                }else if((!TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw))){
                    Toast.makeText(LoginActivity.this,"输入的用户名和密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(LoginActivity.this,"此用户不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String readPsw(String userName){//用SharedPreferences根据用户名读取密码
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getString(userName,"");
    }

    private void saveLoginStatus(boolean status,String userName){//将登录状态和用户名保存到SharedPreferences
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();//获取编辑器
        editor.putBoolean("isLogin",status);
        editor.putString("loginUserName",userName);//存入登录时的用户名
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String userName=data.getStringExtra("userName");//从注册界面传递过来的用户名
            if(!TextUtils.isEmpty(userName)){
                et_user_name.setText(userName);
                et_user_name.setSelection(userName.length());//设置光标位置在用户名长度之后
            }
        }
    }
}
