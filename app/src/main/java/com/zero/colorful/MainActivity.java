package com.zero.colorful;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.simple.spiderman.CrashModel;
import com.simple.spiderman.SpiderMan;
import com.t1.cloud.T1Cloud;
import com.zero.colorful.activity.HomeActivity;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

public class MainActivity extends AppCompatActivity implements Runnable{

    Handler handler=new Handler(  );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        SpiderMan.getInstance()
                .init(this)
                //设置是否捕获异常，不弹出崩溃框
                .setEnable(true)
                //设置是否显示崩溃信息展示页面
                .showCrashMessage(true)
                //是否回调异常信息，友盟等第三方崩溃信息收集平台会用到,
                .setOnCrashListener(new SpiderMan.OnCrashListener() {
                    @Override
                    public void onCrash(Thread t, Throwable ex, CrashModel model) {
                        //CrashModel 崩溃信息记录，包含设备信息
                    }
                });
        LitePal.initialize(this);
        Connector.getDatabase();
        QMUIStatusBarHelper.translucent( this );
        QMUIStatusBarHelper.setStatusBarLightMode( this );
        //弹出崩溃信息展示界面
        String key = "8e10e3c2bf013f6b";
        String token = "2b3ac5bf485712bd0e5675d276838e6f";
        T1Cloud.initialize(this, key, token);// 初始化 T1Cloud SDK

        handler.postDelayed(this, 800);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks( this );
    }


    @Override
    public void run() {
        Intent intent=new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
