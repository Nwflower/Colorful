package com.zero.colorful.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIVerticalTextView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zero.colorful.R;
import com.zero.colorful.fun.ColorFun;
import com.zero.colorful.object.ColorHex;

import org.litepal.LitePal;

import java.util.List;
import java.util.Objects;
import static com.zero.colorful.fun.ColorFun.HEXtoRGB;
import static com.zero.colorful.fun.ColorFun.RGBtoCMYK;
import static com.zero.colorful.fun.ColorFun.RGBtoHSL;
import static com.zero.colorful.fun.ColorFun.RGBtoHSV;
import static com.zero.colorful.fun.ColorFun.RGBtoLab;
import static com.zero.colorful.fun.ColorFun.colorLight;

public class MakeColorActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    TextView colorType1Title,colorType2Title,colorType3Title,colorType4Title,colorType5Title
            ,colorType6Title,colorType1Text,colorType2Text,colorType3Text,colorType4Text,colorType5Text,colorType6Text;
    QMUIVerticalTextView colorName;
    View colorPreview;
    ImageButton refresh,copy,save,seek;
    boolean a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_make_color );

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        a =preferences.getBoolean( "smallWindow" ,false);

        toolbar=this.findViewById( R.id.toolbar );
        toolbar.setTitle( R.string.MakeColorTitle );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled( true ); //设置返回键可用
        QMUIStatusBarHelper.translucent( this );
        colorType1Title=this.findViewById( R.id.color1Title );
        colorType2Title=this.findViewById( R.id.color2Title );
        colorType3Title=this.findViewById( R.id.color3Title );
        colorType4Title=this.findViewById( R.id.color4Title );
        colorType5Title=this.findViewById( R.id.color5Title );
        colorType6Title=this.findViewById( R.id.color6Title );
        colorType1Text=this.findViewById( R.id.color1Text );
        colorType2Text=this.findViewById( R.id.color2Text );
        colorType3Text=this.findViewById( R.id.color3Text );
        colorType4Text=this.findViewById( R.id.color4Text );
        colorType5Text=this.findViewById( R.id.color5Text );
        colorType6Text=this.findViewById( R.id.color6Text );
        colorName=this.findViewById( R.id.colorName );
        refresh=this.findViewById( R.id.refresh );
        copy=this.findViewById( R.id.copy );
        save=this.findViewById( R.id.like );
        seek=this.findViewById( R.id.seekColor );
        colorType1Title.setOnClickListener( this );
        colorType2Title.setOnClickListener( this );
        colorType3Title.setOnClickListener( this );
        colorType4Title.setOnClickListener( this );
        colorType5Title.setOnClickListener( this );
        colorType6Title.setOnClickListener( this );
        colorType1Text.setOnClickListener( this );
        colorType2Text.setOnClickListener( this );
        colorType3Text.setOnClickListener( this );
        colorType4Text.setOnClickListener( this );
        colorType5Text.setOnClickListener( this );
        colorType6Text.setOnClickListener( this );

        if (!a) colorPreview = this.findViewById( R.id.bigColorPreview );
        else colorPreview = this.findViewById( R.id.smallColorPreview );

        Intent intent =getIntent();
        String settingColor=intent.getStringExtra( "colorPreview"  );
        if(settingColor!=null) setColor( settingColor );
        else setColor( ColorFun.getRandomColor() );
        copy.setOnClickListener( this );
        refresh.setOnClickListener( this );
        save.setOnClickListener( this );
        seek.setOnClickListener( this );
    }

    private boolean checkSaved(String color){
        List<ColorHex> colorHexes = LitePal.findAll(ColorHex.class);
        for (ColorHex colorHex:colorHexes) {
            if (color.equals( colorHex.getColorHex() ))
                return true;
        }
        return false;
    }

    private void setColor(String color){
        setTheColorText( color );
        setBackgroundColor( color );
    }
    @Override
    public void onClick(View v) {
        if (v instanceof TextView){
            //复制到剪贴板
            ClipboardManager cm = (ClipboardManager) getSystemService( Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", ((TextView) v).getText());
            Objects.requireNonNull( cm ).setPrimaryClip(mClipData);
            final QMUITipDialog tipDialog;
            tipDialog = new QMUITipDialog.Builder(v.getContext())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                    .setTipWord("复制成功"+((TextView) v).getText())
                    .create();
            tipDialog.show();
            v.postDelayed( tipDialog::dismiss, 1000);
        }else{
            switch (v.getId()){
                case R.id.copy:
                    //Toast.makeText( MakeColorActivity.this,"单击文本即可复制到剪贴板",Toast.LENGTH_LONG).show();
                    ClipboardManager cm = (ClipboardManager) getSystemService( Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Label", colorType1Text.getText());
                    Objects.requireNonNull( cm ).setPrimaryClip(mClipData);
                    final QMUITipDialog tipDialog;
                    tipDialog = new QMUITipDialog.Builder(v.getContext())
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                            .setTipWord("轻触颜色代码即可复制到剪贴板\n已自动复制HEX代码"+colorType1Text.getText())
                            .create();
                    tipDialog.show();
                    v.postDelayed( tipDialog::dismiss, 3000);
                    break;
                case  R.id.refresh:
                    setColor( ColorFun.getRandomColor() );
                    break;
                case R.id.like:
                    if (!checkSaved( colorType1Text.getText().toString() )) {
                        ColorHex colorHex = new ColorHex( "", colorType1Text.getText().toString() );
                        colorHex.save();
                        Toast.makeText( MakeColorActivity.this, "收藏成功" + colorType1Text.getText().toString(), Toast.LENGTH_LONG ).show();
                    }else {
                        LitePal.deleteAll( ColorHex.class, "colorHex = ? " ,colorType1Text.getText().toString() );
                        Toast.makeText( MakeColorActivity.this, "已取消收藏" , Toast.LENGTH_LONG ).show();
                    }
                    setTheTextColor( ColorFun.colorLight( colorType1Text.getText().toString() ) );
                    break;
                case R.id.seekColor:
                    Intent intent = new Intent( MakeColorActivity.this,SeekColorActivity.class );
                    intent.putExtra( "color" , colorType1Text.getText().toString() );
                    startActivityForResult( intent ,1);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                String color = Objects.requireNonNull( data ).getStringExtra( "color" );
                try {
                    setColor( color );
                }catch (Exception ignored){
                }
            }
        }
    }

    private void setBackgroundColor(String color){
        try{
            if (a) {
                Window window = getWindow();
                window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
                window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
                window.setStatusBarColor( Color.parseColor( color ) );
            }
            colorPreview.setBackgroundColor( Color.parseColor( color ) );
            toolbar.setBackgroundColor( Color.parseColor( color ) );
            setTheTextColor( colorLight(color) );
        }catch (Exception e){
            Log.e( "ColorError", e.toString() );
        }
    }
    private void setTheColorText(String color){
        int[] RGB=HEXtoRGB(color);
        String RGBText=RGB[0]+","+RGB[1]+","+RGB[2];
        int[] HSV=RGBtoHSV(RGB[0],RGB[1],RGB[2]);
        String HSVText=HSV[0]+"°,"+HSV[1]+"%,"+HSV[2]+"%";
        int[] CMYK=RGBtoCMYK(RGB[0],RGB[1],RGB[2]);
        String CMYKText=CMYK[0]+"%,"+CMYK[1]+"%,"+CMYK[2]+"%,"+CMYK[3]+"%";
        int[] HSL=RGBtoHSL(RGB[0],RGB[1],RGB[2]);
        String HSLText=HSL[0]+"°,"+HSL[1]+"%,"+HSL[2]+"%";
        int[] Lab=RGBtoLab(RGB[0],RGB[1],RGB[2]);
        String LabText=Lab[0]+","+Lab[1]+","+Lab[2];
        colorType1Text.setText( color );
        colorType2Text.setText( RGBText );
        colorType3Text.setText( HSVText );
        colorType4Text.setText( CMYKText );
        colorType5Text.setText( HSLText );
        colorType6Text.setText( LabText );
    }
    @SuppressLint("PrivateResource")
    private void setTheTextColor(boolean LightColor){
        int color=getResources().getColor( R.color.text_color_in_dark_back );
        Drawable upArrow = getResources().getDrawable( R.drawable.abc_ic_ab_back_material );
        upArrow.setColorFilter( getResources().getColor( R.color.text_color_in_toolBar_back ), PorterDuff.Mode.SRC_ATOP );
        if(LightColor){//白底黑字
            color=getResources().getColor( R.color.text_color_in_light_back );
            upArrow.setColorFilter( getResources().getColor( R.color.text_color_in_light_back ), PorterDuff.Mode.SRC_ATOP );
        }
        Objects.requireNonNull( getSupportActionBar() ).setHomeAsUpIndicator( upArrow );
        toolbar.setTitleTextColor( color );
        if(!a) {
            colorType1Title.setTextColor( color );
            colorType1Text.setTextColor( color );
            colorType2Title.setTextColor( color );
            colorType2Text.setTextColor( color );
            colorType3Title.setTextColor( color );
            colorType3Text.setTextColor( color );
            colorType4Title.setTextColor( color );
            colorType4Text.setTextColor( color );
            colorType5Title.setTextColor( color );
            colorType5Text.setTextColor( color );
            colorType6Title.setTextColor( color );
            colorType6Text.setTextColor( color );
            colorName.setTextColor( color );
            Drawable copyImage = getResources().getDrawable( R.drawable.copy );
            copyImage.setColorFilter( color, PorterDuff.Mode.SRC_ATOP );
            copy.setImageDrawable( copyImage );
            Drawable refreshImage = getResources().getDrawable( R.drawable.refresh );
            refreshImage.setColorFilter( color, PorterDuff.Mode.SRC_ATOP );
            refresh.setImageDrawable( refreshImage );
            Drawable likeImage;
            if( checkSaved( colorType1Text.getText().toString() )) {
                likeImage = getResources().getDrawable( R.drawable.like_clicked );
            }
            else {
                likeImage = getResources().getDrawable( R.drawable.like );
            }
            likeImage.setColorFilter( color, PorterDuff.Mode.SRC_ATOP );
            save.setImageDrawable( likeImage );

            Drawable SeekImage = getResources().getDrawable( R.drawable.seekbar );
            SeekImage.setColorFilter( color, PorterDuff.Mode.SRC_ATOP );
            seek.setImageDrawable( SeekImage );
        }else {
            Drawable likeImage;
            if( checkSaved( colorType1Text.getText().toString() )) {
                likeImage = getResources().getDrawable( R.drawable.like_clicked );
                likeImage.setColorFilter( getResources().getColor( R.color.liked ), PorterDuff.Mode.SRC_ATOP );
            }
            else {
                likeImage = getResources().getDrawable( R.drawable.like );
                likeImage.setColorFilter( getResources().getColor( R.color.text_color_in_general_back ), PorterDuff.Mode.SRC_ATOP );
            }
            save.setImageDrawable( likeImage );
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
