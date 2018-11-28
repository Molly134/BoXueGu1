package com.example.huan.boxuegu1.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huan.boxuegu1.R;
import com.example.huan.boxuegu1.activity.LoginActivity;
import com.example.huan.boxuegu1.activity.SettingActivity;
import com.example.huan.boxuegu1.utils.AnalysisUtils;

/**
 * Created by huan on 2018/10/26.
 */

public class MyInfoView {
    private LinearLayout ll_head;
    public ImageView iv_head_icon;
    private TextView tv_user_name;
    private RelativeLayout rl_course_history_icon,rl_setting;

    private Activity mContext;
    private LayoutInflater mInfater;
    private View mCurrentView;

    public MyInfoView(Activity context){
        mContext=context;
        mInfater=LayoutInflater.from(mContext);//为之后将Layout转化为Vie时用
    }

    private void createView(){
        initView();
    }

    private void initView(){//获取界面控件
        //设置布局文件
        mCurrentView=mInfater.inflate(R.layout.main_view_myinfo,null);
        ll_head=(LinearLayout)mCurrentView.findViewById(R.id.ll_head);
        iv_head_icon=(ImageView)mCurrentView.findViewById(R.id.iv_head_icon);
        rl_course_history_icon=(RelativeLayout)mCurrentView.findViewById(R.id.rl_course_history);
        rl_setting=(RelativeLayout)mCurrentView.findViewById(R.id.rl_setting);
        tv_user_name=(TextView)mCurrentView.findViewById(R.id.tv_user_name);

        mCurrentView.setVisibility(View.VISIBLE);
        setLoginParams(readLoginStatus());//设置登录时界面控件的状态，即是否显示用户名
        ll_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否已经登录
                if(readLoginStatus()){//已登录
                    //跳转到个人资料界面
                }else {//未登录跳转到登录页面
                    Intent intent=new Intent(mContext, LoginActivity.class);
                    mContext.startActivityForResult(intent,1);
                }
            }
        });

        rl_course_history_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readLoginStatus()){//已登录
                    //跳转到播放记录界面
                }else {
                    Toast.makeText(mContext,"您还未登录，请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        rl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readLoginStatus()){//已登录,跳转到设置界面
                    Intent intent=new Intent(mContext, SettingActivity.class);
                    mContext.startActivityForResult(intent,1);
                }else {
                    Toast.makeText(mContext,"您还未登录，请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void setLoginParams(boolean isLogin){//登录成功后设置我的界面
        if(isLogin){
            tv_user_name.setText(AnalysisUtils.readLoginUserName(mContext));
        }else {
            tv_user_name.setText("点击登录");
        }
    }

    public View getView(){//获取导航栏上方显示对应的view
        if(mCurrentView==null){
            createView();
        }
        return mCurrentView;
    }

    public void showView(){//显示当前导航栏上方所对应的view界面
        if(mCurrentView==null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }

    private boolean readLoginStatus(){
        SharedPreferences sp=mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return isLogin;
    }
}
