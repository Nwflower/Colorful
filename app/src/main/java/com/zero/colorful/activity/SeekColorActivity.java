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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zero.colorful.R;
import com.zero.colorful.fun.ColorFun;
import com.zero.colorful.object.ColorHex;

import org.litepal.LitePal;

import java.util.Objects;

public class SeekColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    Toolbar toolbar;
    TextView oldColor,Tr,Tg,Tb,Th,Ts,Tv;
    ImageButton Pr,Pg,Pb,Ph,Ps,Pv;
    EditText newColor;
    SeekBar seekBarR,seekBarG,seekBarB,seekBarH,seekBarS,seekBarV;
    SeekBar.OnSeekBarChangeListener seekBarChangeListener;
    String beforeColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_seek_color );

        toolbar=this.findViewById( R.id.toolbar );
        toolbar.setTitle( R.string.color_seek );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled( true ); //设置返回键可用
        @SuppressLint("PrivateResource") final Drawable upArrow = getResources().getDrawable( R.drawable.abc_ic_ab_back_material );
        upArrow.setColorFilter( getResources().getColor( R.color.text_color_in_toolBar_back ), PorterDuff.Mode.SRC_ATOP );
        getSupportActionBar().setHomeAsUpIndicator( upArrow );
        QMUIStatusBarHelper.translucent( this );

        Intent intent = getIntent();

        beforeColor=intent.getStringExtra( "color" );
        Intent data = new Intent(  );
        data.putExtra( "color" ,beforeColor );
        setResult( RESULT_OK ,data );
        oldColor = this.findViewById( R.id.beforeColor );
        newColor = this.findViewById( R.id.new_color );
        seekBarR = this.findViewById( R.id.seekBarR );
        seekBarG = this.findViewById( R.id.seekBarG );
        seekBarB = this.findViewById( R.id.seekBarB );
        seekBarH = this.findViewById( R.id.seekBarH );
        seekBarS = this.findViewById( R.id.seekBarS );
        seekBarV = this.findViewById( R.id.seekBarV );
        Tr = this.findViewById( R.id.RTextView );
        Tg = this.findViewById( R.id.GTextView );
        Tb = this.findViewById( R.id.BTextView );
        Th = this.findViewById( R.id.HTextView );
        Ts = this.findViewById( R.id.STextView );
        Tv = this.findViewById( R.id.VTextView );
        Pr = this.findViewById( R.id.seekColorR );
        Pg = this.findViewById( R.id.seekColorG );
        Pb = this.findViewById( R.id.seekColorB );
        Ph = this.findViewById( R.id.seekColorH );
        Ps = this.findViewById( R.id.seekColorS );
        Pv = this.findViewById( R.id.seekColorV );
        seekBarR.setOnSeekBarChangeListener( this );
        seekBarG.setOnSeekBarChangeListener( this );
        seekBarB.setOnSeekBarChangeListener( this );
        seekBarH.setOnSeekBarChangeListener( this );
        seekBarS.setOnSeekBarChangeListener( this );
        seekBarV.setOnSeekBarChangeListener( this );
        Pr.setOnClickListener( this );
        Pg.setOnClickListener( this );
        Pb.setOnClickListener( this );
        Ph.setOnClickListener( this );
        Ps.setOnClickListener( this );
        Pv.setOnClickListener( this );
        seekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }};
    }

    @Override
    protected void onStart() {
        super.onStart();
        oldColor.setText( beforeColor );
        if (ColorFun.colorLight( beforeColor ))
            oldColor.setTextColor( getResources().getColor( R.color.text_color_in_light_back  ));
        else oldColor.setTextColor( getResources().getColor( R.color.text_color_in_dark_back  ));
        oldColor.setBackgroundColor( Color.parseColor(beforeColor) );
        newColor.setText( beforeColor );
        newColor.setBackgroundColor( Color.parseColor(beforeColor) );
        seekBarR.setOnSeekBarChangeListener( seekBarChangeListener );
        seekBarG.setOnSeekBarChangeListener( seekBarChangeListener );
        seekBarB.setOnSeekBarChangeListener( seekBarChangeListener );
        seekBarH.setOnSeekBarChangeListener( seekBarChangeListener );
        seekBarS.setOnSeekBarChangeListener( seekBarChangeListener );
        seekBarV.setOnSeekBarChangeListener( seekBarChangeListener );
        int[] RGB = ColorFun.HEXtoRGB( Objects.requireNonNull( beforeColor ) );
        seekBarR.setProgress( RGB[0] );
        seekBarG.setProgress( RGB[1] );
        seekBarB.setProgress( RGB[2] );
        setTextRGB( RGB[0],RGB[1],RGB[2] );
        int[] HSV = ColorFun.RGBtoHSV( RGB[0],RGB[1],RGB[2] );
        seekBarH.setProgress( HSV[0] );
        seekBarS.setProgress( HSV[1] );
        seekBarV.setProgress( HSV[2] );
        setTextHSV( HSV[0],HSV[1],HSV[2] );
        seekBarR.setOnSeekBarChangeListener( this );
        seekBarG.setOnSeekBarChangeListener( this );
        seekBarB.setOnSeekBarChangeListener( this );
        seekBarH.setOnSeekBarChangeListener( this );
        seekBarS.setOnSeekBarChangeListener( this );
        seekBarV.setOnSeekBarChangeListener( this );
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ok:
                Intent data = new Intent(  );
                data.putExtra( "color" ,newColor.getText().toString() );
                setResult( RESULT_OK ,data );
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seek_color_menu, menu);
        return super.onCreateOptionsMenu( menu );
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.seekBarR:
            case R.id.seekBarG:
            case R.id.seekBarB:
                int colorR = seekBarR.getProgress();
                int colorG = seekBarG.getProgress();
                int colorB = seekBarB.getProgress();
                newColor.setText( ColorFun.RGBtoHEX( colorR,colorG,colorB ) );
                setTextRGB( colorR,colorG,colorB );
                int[] HSV = ColorFun.RGBtoHSV( colorR,colorG,colorB );
                seekBarH.setProgress( HSV[0] );
                seekBarS.setProgress( HSV[1] );
                seekBarV.setProgress( HSV[2] );
                setTextHSV( HSV[0],HSV[1],HSV[2] );
                newColor.setBackgroundColor( Color.rgb( colorR,colorG,colorB ) );
                if (ColorFun.colorLight( colorR,colorG,colorB ))
                    newColor.setTextColor( getResources().getColor( R.color.text_color_in_light_back  ));
                else newColor.setTextColor( getResources().getColor( R.color.text_color_in_dark_back  ));
                break;
            case R.id.seekBarH:
            case R.id.seekBarS:
            case R.id.seekBarV:
                int colorH = seekBarH.getProgress();
                int colorS = seekBarS.getProgress();
                int colorV = seekBarV.getProgress();
                int[] RGB = ColorFun.HSVtoRGB( colorH,colorS,colorV );
                setTextHSV( colorH,colorS,colorV );
                seekBarR.setProgress( RGB[0] );
                seekBarG.setProgress( RGB[1] );
                seekBarB.setProgress( RGB[2] );
                setTextRGB( RGB[0],RGB[1],RGB[2] );
                newColor.setText( ColorFun.RGBtoHEX( RGB[0],RGB[1],RGB[2] ) );
                newColor.setBackgroundColor( Color.rgb( RGB[0],RGB[1],RGB[2] ) );
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        int a;
            switch (v.getId()){
                case R.id.seekColorR:
                    seekBarH.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarS.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarV.setOnSeekBarChangeListener( seekBarChangeListener );
                    a = seekBarR.getProgress();
                    a=a+1;
                    seekBarR.setProgress( a );
                    seekBarH.setOnSeekBarChangeListener( this );
                    seekBarS.setOnSeekBarChangeListener( this );
                    seekBarV.setOnSeekBarChangeListener( this );
                    break;
                case R.id.seekColorG:
                    seekBarH.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarS.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarV.setOnSeekBarChangeListener( seekBarChangeListener );
                    a = seekBarG.getProgress();
                    a=a+1;
                    seekBarG.setProgress( a );
                    seekBarH.setOnSeekBarChangeListener( this );
                    seekBarS.setOnSeekBarChangeListener( this );
                    seekBarV.setOnSeekBarChangeListener( this );
                    break;
                case R.id.seekColorB:
                    seekBarH.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarS.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarV.setOnSeekBarChangeListener( seekBarChangeListener );
                    a = seekBarB.getProgress();
                    a=a+1;
                    seekBarB.setProgress( a );
                    seekBarH.setOnSeekBarChangeListener( this );
                    seekBarS.setOnSeekBarChangeListener( this );
                    seekBarV.setOnSeekBarChangeListener( this );
                    break;
                case R.id.seekColorH:
                    seekBarR.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarG.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarB.setOnSeekBarChangeListener( seekBarChangeListener );
                    a = seekBarH.getProgress();
                    a=a+1;
                    seekBarH.setProgress( a );
                    seekBarH.setOnSeekBarChangeListener( this );
                    seekBarS.setOnSeekBarChangeListener( this );
                    seekBarV.setOnSeekBarChangeListener( this );
                    break;
                case R.id.seekColorS:
                    seekBarR.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarG.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarB.setOnSeekBarChangeListener( seekBarChangeListener );
                    a = seekBarS.getProgress();
                    a=a+1;
                    seekBarS.setProgress( a );
                    seekBarH.setOnSeekBarChangeListener( this );
                    seekBarS.setOnSeekBarChangeListener( this );
                    seekBarV.setOnSeekBarChangeListener( this );
                    break;
                case R.id.seekColorV:
                    seekBarR.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarG.setOnSeekBarChangeListener( seekBarChangeListener );
                    seekBarB.setOnSeekBarChangeListener( seekBarChangeListener );
                    a = seekBarV.getProgress();
                    a=a+1;
                    seekBarV.setProgress( a );
                    seekBarH.setOnSeekBarChangeListener( this );
                    seekBarS.setOnSeekBarChangeListener( this );
                    seekBarV.setOnSeekBarChangeListener( this );
                    break;

        }
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.seekBarR:
            case R.id.seekBarG:
            case R.id.seekBarB:
                seekBarH.setOnSeekBarChangeListener( seekBarChangeListener );
                seekBarS.setOnSeekBarChangeListener( seekBarChangeListener );
                seekBarV.setOnSeekBarChangeListener( seekBarChangeListener );
                break;
            case R.id.seekBarH:
            case R.id.seekBarS:
            case R.id.seekBarV:
                seekBarR.setOnSeekBarChangeListener( seekBarChangeListener );
                seekBarG.setOnSeekBarChangeListener( seekBarChangeListener );
                seekBarB.setOnSeekBarChangeListener( seekBarChangeListener );
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.seekBarR:
            case R.id.seekBarG:
            case R.id.seekBarB:
                seekBarH.setOnSeekBarChangeListener( this );
                seekBarS.setOnSeekBarChangeListener( this );
                seekBarV.setOnSeekBarChangeListener( this );
                break;
            case R.id.seekBarH:
            case R.id.seekBarS:
            case R.id.seekBarV:
                seekBarR.setOnSeekBarChangeListener( this );
                seekBarG.setOnSeekBarChangeListener( this );
                seekBarB.setOnSeekBarChangeListener( this );
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void setTextRGB(int R, int G, int B){
        Tr.setText( "R:"+R );
        Tg.setText( "G:"+G );
        Tb.setText( "B:"+B );
    }
    @SuppressLint("SetTextI18n")
    public void setTextHSV(int H, int S, int V){
        Th.setText( "H:"+H );
        Ts.setText( "S:"+S );
        Tv.setText( "V:"+V );
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
