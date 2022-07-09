package com.zero.colorful.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIVerticalTextView;
import com.zero.colorful.R;
import com.zero.colorful.adapter.CollocationAdapter;
import com.zero.colorful.object.ColorHex;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.zero.colorful.fun.ColorFun.HEXtoRGB;
import static com.zero.colorful.fun.ColorFun.RGBtoHSV;
import static com.zero.colorful.fun.ColorFun.colorLight;

public class collocationActivity extends AppCompatActivity {
    private List<ColorHex> CollocationList = new ArrayList<>();
    TextView HexTitle,RgbTitle,HsvTitle,HexText,RgbText,HsvText;
    QMUIVerticalTextView colorName;
    Toolbar toolbar;
    private ConstraintLayout backgroundColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_collocation );
        toolbar=this.findViewById( R.id.toolbar );
        colorName=this.findViewById( R.id.colorName );
        HexTitle = this.findViewById( R.id.color1Title );
        HexText = this.findViewById( R.id.color1Text );
        RgbTitle = this.findViewById( R.id.color2Title );
        RgbText = this.findViewById( R.id.color2Text );
        HsvTitle = this.findViewById( R.id.color3Title );
        HsvText = this.findViewById( R.id.color3Text );
        backgroundColor=this.findViewById( R.id.backgroundColor );
        getData();
        QMUIStatusBarHelper.translucent( this );
        changeColor( 0 );
    }
    @SuppressLint("PrivateResource")
    public void getData(){
        Intent intent=getIntent();
        toolbar.setTitle( intent.getStringExtra( "title" ) );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled( true ); //设置返回键可用
        Drawable upArrow = getResources().getDrawable( R.drawable.abc_ic_ab_back_material );
        upArrow.setColorFilter( getResources().getColor( R.color.text_color_in_toolBar_back ), PorterDuff.Mode.SRC_ATOP );
        getSupportActionBar().setHomeAsUpIndicator( upArrow );
        int path=intent.getIntExtra( "color" ,R.raw.chinesecolor);
        Resources res = super.getResources();
        InputStream input = res.openRawResource(path);
        Scanner scan = new Scanner(input);
        StringBuilder buf = new StringBuilder();
        while (scan.hasNext()) {
            buf.append(scan.next()).append("\n");
        }
        scan.close();
        try {
            input.close() ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string=buf.toString();
        final String[] collocation = string.split( "\n" );
        for (String s : collocation) {
            final String[] color = s.split( "-" );
            ColorHex colorHex = new ColorHex( color[1], color[0] );
            CollocationList.add( colorHex );
        }


        RecyclerView recyclerView=this.findViewById( R.id.collocation_recycler_view );
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        CollocationAdapter adapter = new CollocationAdapter(CollocationList);
        adapter.setOnItemClickListener(new CollocationAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeColor( position );
            }
        });
        recyclerView.setAdapter(adapter);
    }
    @SuppressLint("PrivateResource")
    public void changeColor(int position){
        colorName.setText( CollocationList.get(position).getName( ));
        String HEX=CollocationList.get( position ).getColorHex();
        HexText.setText( HEX );
        int[] RGB=HEXtoRGB(HEX);
        String RGBText=RGB[0]+","+RGB[1]+","+RGB[2];
        RgbText.setText( RGBText );
        int[] HSV=RGBtoHSV(RGB[0],RGB[1],RGB[2]);
        String HSVText=HSV[0]+"°,"+HSV[1]+"%,"+HSV[2]+"%";
        HsvText.setText( HSVText );
        backgroundColor.setBackgroundColor( Color.parseColor( HEX ) );
        toolbar.setBackgroundColor( Color.parseColor( HEX ) );
        int color;
        final Drawable upArrow = getResources().getDrawable( R.drawable.abc_ic_ab_back_material );
        if (colorLight( HEX )){
            color= getResources().getColor( R.color.text_color_in_light_back ) ;
            QMUIStatusBarHelper.setStatusBarLightMode( collocationActivity.this );
        }else {
            color= getResources().getColor( R.color.text_color_in_dark_back ) ;
            QMUIStatusBarHelper.setStatusBarDarkMode( collocationActivity.this );
        }
        upArrow.setColorFilter( color, PorterDuff.Mode.SRC_ATOP );
        Objects.requireNonNull( getSupportActionBar() ).setHomeAsUpIndicator( upArrow );
        toolbar.setTitleTextColor( color );
        colorName.setTextColor( color );
        HexTitle.setTextColor( color);
        RgbTitle.setTextColor( color);
        HsvTitle.setTextColor( color);
        HexText.setTextColor( color);
        RgbText.setTextColor( color);
        HsvText.setTextColor( color);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
