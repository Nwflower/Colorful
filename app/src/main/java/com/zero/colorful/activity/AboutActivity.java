package com.zero.colorful.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.zero.colorful.R;
import com.zero.colorful.view.CircleImageView;
import java.util.Objects;
import static com.zero.colorful.fun.AboutFun.getAppVersionCode;
import static com.zero.colorful.fun.AboutFun.getAppVersionName;
import static com.zero.colorful.fun.AboutFun.getNew;
import static com.zero.colorful.fun.AboutFun.joinQQGroup;

public class AboutActivity extends AppCompatActivity {
    String stringVersion;
    boolean isGetNew=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about );

        Toolbar toolbar = this.findViewById( R.id.toolbar );
        toolbar.setTitle( R.string.about );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled( true ); //设置返回键可用
        @SuppressLint("PrivateResource") final Drawable upArrow = getResources().getDrawable( R.drawable.abc_ic_ab_back_material );
        upArrow.setColorFilter( getResources().getColor( R.color.text_color_in_toolBar_back ), PorterDuff.Mode.SRC_ATOP );
        getSupportActionBar().setHomeAsUpIndicator( upArrow );

        stringVersion = getAppVersionName( this ) + " (" + getAppVersionCode( this ) + ")";

        View information = this.findViewById( R.id.information );
        final View getNew = this.findViewById( R.id.getNew );
        View qqGroup = this.findViewById( R.id.qqGroup );
        View developer1 = this.findViewById( R.id.developer1 );
        View developer2 = this.findViewById( R.id.developer2 );
        View developer3 = this.findViewById( R.id.developer3 );
        View friend1 = this.findViewById( R.id.friend1 );
        TextView versionTitle = information.findViewById( R.id.card_title );
        versionTitle.setText( R.string.app_version );
        TextView versionText = information.findViewById( R.id.card_text );
        versionText.setText( stringVersion );
        TextView getNewTitle = getNew.findViewById( R.id.card_title );
        getNewTitle.setText( R.string.item_getNew );
        TextView getNewText = getNew.findViewById( R.id.card_text );
        getNewText.setText( R.string.item_getNewText );
        CircleImageView getNewImage = getNew.findViewById( R.id.card_image );
        getNewImage.setImageResource( R.drawable.about_getnew );
        ConstraintLayout getNewCard = getNew.findViewById( R.id.card );
        getNewCard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGetNew) {
                    isGetNew = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission( AboutActivity.this, Manifest.permission.INTERNET ) != PackageManager.PERMISSION_GRANTED) {
                            // 检查权限状态
                            if (ActivityCompat.shouldShowRequestPermissionRationale( AboutActivity.this, Manifest.permission.INTERNET )) {
                                //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                                Toast.makeText( AboutActivity.this, "获取网络权限失败", Toast.LENGTH_LONG ).show();
                            } else {
                                //  用户未彻底拒绝授予权限
                                ActivityCompat.requestPermissions( AboutActivity.this, new String[]{Manifest.permission.INTERNET}, 1 );
                            }
                        }
                    }
                    if (!getNew( AboutActivity.this )) {
                        isGetNew = false;
                    }
                }
            }
        } );
        TextView qqGroupTitle = qqGroup.findViewById( R.id.card_title );
        qqGroupTitle.setText( R.string.app_qq_group );
        TextView qqGroupText = qqGroup.findViewById( R.id.card_text );
        qqGroupText.setText( R.string.app_qq_group_text );
        CircleImageView qqGroupImage = qqGroup.findViewById( R.id.card_image );
        qqGroupImage.setImageResource( R.drawable.qq_group );
        ConstraintLayout qqGroupCard = qqGroup.findViewById( R.id.card );
        qqGroupCard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!joinQQGroup( "Z-0smezD5cYmKVUxH5oxNfzf9YwEMu2E", AboutActivity.this )) {
                    Toast.makeText( AboutActivity.this, "你没有安装QQ或TIM", Toast.LENGTH_LONG ).show();
                }
            }
        } );
        TextView developer1Title = developer1.findViewById( R.id.card_title );
        developer1Title.setText( R.string.app_developer1 );
        TextView developer1Text = developer1.findViewById( R.id.card_text );
        developer1Text.setText( R.string.app_developer1Text );
        CircleImageView developer1Image = developer1.findViewById( R.id.card_image );
        developer1Image.setImageResource( R.drawable.app_developer1 );
        ConstraintLayout developer1Card = developer1.findViewById( R.id.card );
        developer1Card.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent=new Intent( Intent.ACTION_VIEW,Uri.parse( "coolmarket://u/2308474" ) )
                            .setPackage( "com.coolapk.market" )
                            .addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity( intent );
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent intent= new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse( "https://www.coolapk.com/u/2308474" );
                    intent.setData(content_url);
                    startActivity(intent);
                }
            }
        } );
        TextView developer2Title = developer2.findViewById( R.id.card_title );
        developer2Title.setText( R.string.app_developer2 );
        TextView developer2Text = developer2.findViewById( R.id.card_text );
        developer2Text.setText( R.string.app_developer2Text );
        CircleImageView developer2Image = developer2.findViewById( R.id.card_image );
        developer2Image.setImageResource( R.drawable.app_developer2 );
        ConstraintLayout developer2Card = developer2.findViewById( R.id.card );
        developer2Card.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=2652330984";//uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText( AboutActivity.this,"未安装QQ",Toast.LENGTH_LONG ).show();
                }
            }
        } );
        TextView developer3Title = developer3.findViewById( R.id.card_title );
        developer3Title.setText( R.string.app_developer3 );
        TextView developer3Text = developer3.findViewById( R.id.card_text );
        developer3Text.setText( R.string.app_developer3Text );
        CircleImageView developer3Image = developer3.findViewById( R.id.card_image );
        developer3Image.setImageResource( R.drawable.app_developer3 );
        ConstraintLayout developer3Card=developer3.findViewById( R.id.card );
        developer3Card.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent=new Intent( Intent.ACTION_VIEW,Uri.parse( "coolmarket://u/2830293" ) )
                            .setPackage( "com.coolapk.market" )
                            .addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity( intent );
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent intent= new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse( "https://www.coolapk.com/u/2830293" );
                    intent.setData(content_url);
                    startActivity(intent);
                }
            }
        } );

        TextView friend1Title = friend1.findViewById( R.id.card_title );
        friend1Title.setText( R.string.friend1Title );
        TextView friend1Text = friend1.findViewById( R.id.card_text );
        friend1Text.setText( R.string.friend1Text );
        CircleImageView friend1Image = friend1.findViewById( R.id.card_image );
        friend1Image.setImageResource( R.drawable.friend1 );
        ConstraintLayout friend1Card=friend1.findViewById( R.id.card );
        friend1Card.setOnClickListener( v -> {
            try {
                Intent intent=new Intent( Intent.ACTION_VIEW,Uri.parse( "coolmarket://apk/H.Wind" ) )
                        .setPackage( "com.coolapk.market" )
                        .addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity( intent );
            } catch (Exception e) {
                e.printStackTrace();
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse( "https://www.coolapk.com/apk/H.Wind" );
                intent.setData(content_url);
                startActivity(intent);
            }
        } );
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
