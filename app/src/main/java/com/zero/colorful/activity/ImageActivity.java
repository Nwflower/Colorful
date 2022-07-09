package com.zero.colorful.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.qmuiteam.qmui.util.QMUIDrawableHelper;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.zero.colorful.R;
import com.zero.colorful.fun.ColorFun;
import com.zero.colorful.object.ColorHex;

import org.litepal.LitePal;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class ImageActivity extends AppCompatActivity {
    private TextView HEXtext1;
    private ImageView iv_image;
    Bitmap bitmap;
    public static final int NONE = 0;
    public static final int Take = 1;
    public static final int PHOTOZOOM = 2; // 相册，1为拍照
    double ImageX,ImageY,BitmapX,BitmapY;
    Toolbar toolbar;
    private float lastX;
    private float lastY;
    boolean a;
    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_image );

        intiToolbar();

        HEXtext1 =  findViewById(R.id.textview);
        QMUIRoundButton saveColor = findViewById( R.id.savecolor );
        iv_image =  this.findViewById(R.id.iv_image);
        ImageView select = this.findViewById( R.id.select2 );
        /*select.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        lastX=event.getRawX();
                        lastY=event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.setX(v.getX()+event.getRawX()-lastX);
                        v.setY(v.getY()+event.getRawY()-lastY);
                        lastX=event.getRawX();
                        lastY=event.getRawY();

                        try {
                            //int x = (int) event.getX();-lastX+event.getRawX()
                            //int y = (int) event.getY();-lastY+event.getRawY()
                            int x = (int) (iv_image.getX()+v.getX()-select.getWidth()/2+20);
                            int y = (int) (-iv_image.getY()+v.getY()-select.getHeight()/2+56);
                            toolbar.setTitle( iv_image.getX() + "+" + iv_image.getY() );
                            saveColor.setText( (int)v.getX()+"+"+(int)v.getY() );
                            HEXtext1.setText( x + "+" + y );
                            //select.setX( x );
                            //select.setY( y );
                            ImageX = iv_image.getWidth();
                            ImageY = iv_image.getHeight();
                            BitmapX = bitmap.getWidth();
                            BitmapY = bitmap.getHeight();
                            x = ImageX == BitmapX||ImageY == BitmapY? x:(int) ((BitmapX/ImageX)*x);
                            y = ImageX == BitmapX||ImageY == BitmapY? y:(int) ((BitmapY/ImageY)*y);
                            int color = bitmap.getPixel( x, y );
                            int r = Color.red( color );
                            int g = Color.green( color );
                            int b = Color.blue( color );
                            //HEXtext1.setText( ColorFun.RGBtoHEX( r,g,b ) );
                            HEXtext1.setBackgroundColor( Color.rgb( r,g,b ) );
                            BitmapDrawable bitmap = QMUIDrawableHelper.createDrawableWithSize(null,100,100,50, Color.rgb( r,g,b ));
                            select.setBackground( bitmap );
                        }catch (IllegalArgumentException ignored){ }

                        break;
                }
                return true;
            }
        });*/
        saveColor.setOnClickListener( v -> {
            String HEX = HEXtext1.getText().toString();
            if (!checkSaved( HEX ) ) {
                ColorHex colorHex = new ColorHex( "", HEX );
                colorHex.save();
                Toast.makeText( this, "保存成功：" + HEX, Toast.LENGTH_LONG ).show();
            }else Toast.makeText( this, "颜色已保存", Toast.LENGTH_LONG ).show();
        } );

        iv_image.setOnTouchListener( (v, event) -> {
            try {
                int x = (int) event.getX();
                int y = (int) event.getY();
                select.setX( x );
                select.setY( y );
                ImageX = iv_image.getWidth();
                ImageY = iv_image.getHeight();
                BitmapX = bitmap.getWidth();
                BitmapY = bitmap.getHeight();
                x = ImageX == BitmapX||ImageY == BitmapY? x:(int) ((BitmapX/ImageX)*x);
                y = ImageX == BitmapX||ImageY == BitmapY? y:(int) ((BitmapY/ImageY)*y);
                int color = bitmap.getPixel( x, y );
                int r = Color.red( color );
                int g = Color.green( color );
                int b = Color.blue( color );
                HEXtext1.setText( ColorFun.RGBtoHEX( r,g,b ) );
                HEXtext1.setBackgroundColor( Color.rgb( r,g,b ) );
                BitmapDrawable bitmap = QMUIDrawableHelper.createDrawableWithSize(null,100,100,50, Color.rgb( r,g,b ));
                select.setBackground( bitmap );
            }catch (IllegalArgumentException ignored){ }
            return true;
        } );
            iv_image.setImageDrawable( getResources().getDrawable( R.drawable.color ) );
            bitmap = ((BitmapDrawable) iv_image.getDrawable()).getBitmap();
    }

    private void intiToolbar() {
        toolbar = this.findViewById( R.id.toolbar );
        toolbar.setTitle( R.string.GetColorTitle );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled( true ); //设置返回键可用
        @SuppressLint("PrivateResource") Drawable upArrow = getResources().getDrawable( R.drawable.abc_ic_ab_back_material );
        upArrow.setColorFilter( getResources().getColor( R.color.text_color_in_toolBar_back ), PorterDuff.Mode.SRC_ATOP );
        getSupportActionBar().setHomeAsUpIndicator( upArrow );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.image:
                openAlbum(PHOTOZOOM);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_color_menu, menu);
        return super.onCreateOptionsMenu( menu );
    }

    // 打开相册
    private void openAlbum(int way) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, way);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == NONE || data == null)
            return;
        if (requestCode == PHOTOZOOM) {
            ContentResolver contentResolver = getContentResolver();
            try {
                iv_image.setImageBitmap( BitmapFactory.decodeStream( contentResolver.openInputStream( Objects.requireNonNull( data.getData() ) ) ) );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap = ((BitmapDrawable) iv_image.getDrawable()).getBitmap();
        }
    }

    private boolean checkSaved(String color){
        List<ColorHex> colorHexes = LitePal.findAll(ColorHex.class);
        for (ColorHex colorHex:colorHexes) {
            if (color.equals( colorHex.getColorHex() ))
                return true;
        }
        return false;
    }
}

