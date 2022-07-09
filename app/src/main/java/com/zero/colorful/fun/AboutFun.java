package com.zero.colorful.fun;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.t1.cloud.T1CloudFeatures;
import com.t1.cloud.listener.T1CloudListener;
import com.zero.colorful.activity.AboutActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutFun {
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) return "";
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    public static int getAppVersionCode(Context context) {
        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
            if (versioncode == 0 ) {
                return 0;
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }
    public static boolean joinQQGroup(String key,Context context) {
        Intent intent = new Intent();
        intent.setData( Uri.parse( "mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key ) );
        try {
            context.startActivity( intent );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean getNew(final Context context){
        //新建一个 T1CloudFeatures()
        T1CloudFeatures t1CloudFeatures = new T1CloudFeatures();
        t1CloudFeatures.put("id", "tgkekpm");
        final boolean[] successful = {true};
        t1CloudFeatures.cloudDocument(new T1CloudListener() {
            @Override
            public void onDone(String json) {
                successful[0] =getNewJson( json,context );
            }

            @Override
            public void onError(String error) {
                successful[0] =getNewJson( error,context );
            }
        });
        return successful[0];
    }

    public static boolean getNewJson (String json, final Context context){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String text = jsonObject.getString("text");
            final String[]  getNew=text.split( "\n" );
            int code= Integer.parseInt( getNew[1] );
            if (code<=getAppVersionCode( context )){
                Toast.makeText( context, "已是最新版本", Toast.LENGTH_LONG ).show();
                return false;
                //最新版本或内测版本
            }
            StringBuilder message = new StringBuilder();
            for (int x=3;x<getNew.length;x++) {
                message.append( "\n" ).append( getNew[x] );
            }
            new QMUIDialog.MessageDialogBuilder(context)
                    .setTitle(getNew [0])
                    .setMessage(message)
                    .addAction("放弃更新", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }

                    })
                    .addAction("现在更新", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick (QMUIDialog dialog,int index){
                            dialog.dismiss();
                            Intent intent= new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(getNew[2]);
                            intent.setData(content_url);
                            context.startActivity(intent);
                        }})
                    .show();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    // 新建一个 T1CloudRegister()
                /*T1CloudRegister t1CloudRegister = new T1CloudRegister();

                t1CloudRegister.put("user", "1"); // 账号
                t1CloudRegister.put("pass", "pzl3338491901"); // 密码

                t1CloudRegister.register(new T1CloudListener() {
                    @Override
                    public void onDone(String json) {
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String text = jsonObject.getString("message");
                            new QMUIDialog.MessageDialogBuilder(AboutActivity.this)
                                    .setTitle("更新内容")
                                    .setMessage(text)
                                    .addAction("确定", new QMUIDialogAction.ActionListener() {
                                        @Override
                                        public void onClick(QMUIDialog dialog, int index) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            new QMUIDialog.MessageDialogBuilder(AboutActivity.this)
                                    .setTitle("更新内容")
                                    .setMessage("解析失败")
                                    .addAction("确定", new QMUIDialogAction.ActionListener() {
                                        @Override
                                        public void onClick(QMUIDialog dialog, int index) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        // 注册账号失败
                        new QMUIDialog.MessageDialogBuilder(AboutActivity.this)
                                .setTitle("更新内容")
                                .setMessage(error)
                                .addAction("确定", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });*/
}
