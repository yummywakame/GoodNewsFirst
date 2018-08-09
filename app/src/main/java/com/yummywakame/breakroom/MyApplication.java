package com.yummywakame.breakroom;

import android.app.Application;
import android.content.Context;

/**
 * GoodNewsFirst
 * Created by Olivia Meiring on 2018/08/06.
 * Yummy Wakame
 * olivia@yummy-wakame.com
 */
public class MyApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }
}
