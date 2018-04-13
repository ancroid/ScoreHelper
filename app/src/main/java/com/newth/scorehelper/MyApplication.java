package com.newth.scorehelper;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by Mr.chen on 2018/3/18.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Bmob.initialize(this, "02cc601620fa005ac9d6898f949f46d7");
    }
    public static Context getContext() {
        return context;
    }
}
