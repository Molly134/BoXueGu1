package com.example.huan.boxuegu1.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by huan on 2018/10/26.
 */

public class AnalysisUtils {//从SharedPreferences中读取登录用户名

    public static String readLoginUserName(Context context){
        SharedPreferences sp=context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String userName=sp.getString("loginUserName","");
        return userName;
    }

}
