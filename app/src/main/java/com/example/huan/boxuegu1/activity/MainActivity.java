package com.example.huan.boxuegu1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huan.boxuegu1.R;
import com.example.huan.boxuegu1.views.MyInfoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout mBodyLayout;//中间内容栏
    public LinearLayout mBottomLayout;//底部按钮栏

    //底部按钮
    private View mCourseBtn;
    private View mExercisesBtn;
    private View mMyInfoBtn;
    private TextView tv_course;
    private TextView tv_exercises;
    private TextView tv_myInfo;
    private ImageView iv_course;
    private ImageView iv_exercises;
    private ImageView iv_myInfo;

    //顶部标题栏
    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;

    //加载我的界面的view
    private MyInfoView mMyInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置界面为竖屏
        init();//初始化界面组件
        initBottomBar();//底部按钮栏初始化
        setListener();//底部按钮监听事件
        setInitStatus();//设置界面view的初始状态
    }

    private void init(){//界面组件初始化
        //顶部标题栏初始化
        tv_back=(TextView) findViewById(R.id.tv_back);
        tv_main_title=(TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("博学谷课程");
        rl_title_bar=(RelativeLayout)findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30b4ff"));
        tv_back.setVisibility(View.GONE);

        //中间内容栏初始化
        initBodyLayout();
    }

    private void initBodyLayout(){//中间内容栏初始化
        mBodyLayout=(FrameLayout)findViewById(R.id.main_body);
    }

    private void initBottomBar(){//底部按钮栏初始化
        mBottomLayout=(LinearLayout)findViewById(R.id.main_bottom_bar);

        mCourseBtn=findViewById(R.id.bottom_bar_course_btn);
        mExercisesBtn=findViewById(R.id.bottom_bar_exercises_btn);
        mMyInfoBtn=findViewById(R.id.bottom_bar_myinfo_btn);

        tv_course=(TextView)findViewById(R.id.bottom_bar_text_course);
        tv_exercises=(TextView)findViewById(R.id.bottom_bar_text_exercises);
        tv_myInfo=(TextView)findViewById(R.id.bottom_bar_text_myinfo);
        iv_course=(ImageView)findViewById(R.id.bottom_bar_image_course);
        iv_exercises=(ImageView)findViewById(R.id.bottom_bar_image_exercises);
        iv_myInfo=(ImageView)findViewById(R.id.bottom_bar_image_myinfo);
    }


    @Override
    public void onClick(View v) {//控件的点击事件
        switch (v.getId()){
            //课程点击事件
            case R.id.bottom_bar_course_btn:
                clearBottomImageState();
                selectDisplayView(0);
                break;
            //习题点击事件
            case R.id.bottom_bar_exercises_btn:
                clearBottomImageState();
                selectDisplayView(1);
                break;
            //我的点击事件
            case R.id.bottom_bar_myinfo_btn:
                clearBottomImageState();
                selectDisplayView(2);
                break;
            default:
                break;
        }
    }

    private void setListener(){//底部三个按钮的监听事件
        for(int i=0;i<mBottomLayout.getChildCount();i++){
            mBottomLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    private void clearBottomImageState(){//清除底部按钮选中状态
        //把三个按钮字体颜色全部变成灰色
        tv_course.setTextColor(Color.parseColor("#666666"));
        tv_exercises.setTextColor(Color.parseColor("#666666"));
        tv_myInfo.setTextColor(Color.parseColor("#666666"));
        //把三个按钮图片颜色全部变成灰色图片
        iv_course.setImageResource(R.drawable.main_course_icon);
        iv_exercises.setImageResource(R.drawable.main_exercises_icon);
        iv_myInfo.setImageResource(R.drawable.main_my_icon);

        for(int i=0;i<mBottomLayout.getChildCount();i++){
            mBottomLayout.getChildAt(i).setSelected(false);
        }
    }

    private void setSelectedStatus(int index){//设置底部按钮选中状态
        switch (index){
            case 0:
                mCourseBtn.setSelected(true);
                iv_course.setImageResource(R.drawable.main_course_icon_selected);
                tv_course.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);//可视化标题栏
                tv_main_title.setText("博学谷课程");
                break;
            case 1:
                mExercisesBtn.setSelected(true);
                iv_exercises.setImageResource(R.drawable.main_exercises_icon_selected);
                tv_exercises.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);//可视化标题栏
                tv_main_title.setText("博学谷习题");
                break;
            case 2:
                mMyInfoBtn.setSelected(true);
                iv_myInfo.setImageResource(R.drawable.main_my_icon_selected);
                tv_myInfo.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.GONE);//隐藏标题栏
                break;
        }
    }

    private void removeAllView() {//清除中间内容栏不需要的视图
        for(int i=0;i<mBodyLayout.getChildCount();i++){
            mBodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }

    private void createView(int viewIndex){//选择视图
        switch (viewIndex){
            case 0:
                //课程界面
                break;
            case 1:
                //习题界面
                break;

            case 2://我的界面
                if(mMyInfoView==null){
                    mMyInfoView=new MyInfoView(this);
                    mBodyLayout.addView(mMyInfoView.getView());
                }else {
                    mMyInfoView.getView();
                }
                mMyInfoView.showView();
                break;
        }
    }

    private void setInitStatus(){//设置界面view的初始状态
        clearBottomImageState();//清除底部按钮选中状态
        setSelectedStatus(0);//设置底部按钮选中状态，默认选择课程按钮
        createView(0);//选择视图，默认选择课程视图
    }

    private void selectDisplayView(int index){//显示对应的页面
        removeAllView();//清除中间内容栏不需要的视图
        createView(index);//选择视图
        setSelectedStatus(index);//设置底部按钮选中状态
    }

    protected long exitTime;//记录第一次点击时的时间
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis()-exitTime>2000){
                Toast.makeText(MainActivity.this,"再按一次退出博学谷",Toast.LENGTH_SHORT).show();
                exitTime=System.currentTimeMillis();
            }else {
                MainActivity.this.finish();
                if(readLoginStatus()){
                    clearLoginStatus();
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean readLoginStatus(){//获取SharedPreferences中的登录状态
        SharedPreferences sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return isLogin;
    }

    private void clearLoginStatus(){//清除SharedPreferences中的登录状态
        SharedPreferences sp=getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();//获取编辑器
        editor.putBoolean("isLogin",false);//清除登录状态
        editor.putString("loginUserName","");//清除登录时的用户名
        editor.commit();//提交修改
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //从设置界面或登录界面传递过来的登录状态
            boolean islogin=data.getBooleanExtra("isLogin",false);
            if(islogin){//登录成功时显示课程界面
                clearBottomImageState();
                selectDisplayView(0);
            }
            if(mMyInfoView!=null){//登录成功或退出登录时根据isLogin设置我的界面
                mMyInfoView.setLoginParams(islogin);
            }
        }
    }
}
