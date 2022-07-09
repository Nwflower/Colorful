package com.zero.colorful.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDrawableHelper;
import com.simple.spiderman.CrashActivity;
import com.zero.colorful.R;

import java.util.Objects;

public class GradientActivity extends AppCompatActivity {

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gradient );

        Toolbar toolbar = this.findViewById( R.id.toolbar );
        toolbar.setTitle( R.string.gradient );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled( true ); //设置返回键可用
        @SuppressLint("PrivateResource") final Drawable upArrow = getResources().getDrawable( R.drawable.abc_ic_ab_back_material );
        upArrow.setColorFilter( getResources().getColor( R.color.text_color_in_toolBar_back ), PorterDuff.Mode.SRC_ATOP );
        getSupportActionBar().setHomeAsUpIndicator( upArrow );

        LinearLayout colorPreview=this.findViewById( R.id.backgroundColor );
        final ImageView startImage=this.findViewById( R.id.startColor )
                ,endImage=this.findViewById( R.id.endColor );
        final TextView startText=this.findViewById( R.id.startText )
                ,endText=this.findViewById( R.id.endText );

        Intent intent=getIntent();
        String color = intent.getStringExtra( "color" );
        String[] gradient = Objects.requireNonNull( color ).split( "#" );
        int[] colors=new int[gradient.length-1];
        for (int x=1;x<gradient.length;x++){
            int a= Color.parseColor( "#"+gradient[x] );
            colors[x-1]=a;
        }

        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,colors);
        drawable.setCornerRadius(10);
        drawable.setGradientType( GradientDrawable.RECTANGLE);
        colorPreview.setBackground(drawable);

        startText.setText( "#"+gradient[1] );
        endText.setText( "#"+gradient[2] );
        startImage.setImageDrawable(  QMUIDrawableHelper
                .createDrawableWithSize( null,200,200,20,colors[0] ) );
        endImage.setImageDrawable(  QMUIDrawableHelper
                .createDrawableWithSize( null,200,200,20,colors[1] ) );
        startImage.setOnClickListener( v -> {
            Intent intent1 =new Intent( GradientActivity.this,MakeColorActivity.class );
            intent1.putExtra( "colorPreview" , startText.getText() );
            startActivity( intent1 );
        } );
        endImage.setOnClickListener( v -> {
            Intent intent1 =new Intent( GradientActivity.this,MakeColorActivity.class );
            intent1.putExtra( "colorPreview" , endText.getText() );
            startActivity( intent1 );
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
