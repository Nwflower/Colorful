package com.zero.colorful.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zero.colorful.fragment.ColorFragment;
import com.zero.colorful.fragment.InspirationFragment;
import com.zero.colorful.R;
import com.zero.colorful.adapter.HomeFragmentAdapter;
import com.zero.colorful.fragment.MineFragment;
import com.zero.colorful.fragment.PersonalFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout home_tab_inspiration,home_tab_personal,home_tab_color,home_tab_mine;
    private ImageButton home_tab_inspiration_image,home_tab_personal_image,home_tab_color_image,home_tab_mine_image;
    private TextView home_tab_inspiration_text,home_tab_personal_text,home_tab_color_text,home_tab_mine_text;
    private ViewPager homeViewPager;
    private long exitTime = 2000;//返回间隔
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        home_tab_inspiration=this.findViewById( R.id.home_tab_inspiration );
        home_tab_personal=this.findViewById( R.id.home_tab_personal );
        home_tab_color=this.findViewById( R.id.home_tab_color );
        home_tab_mine=this.findViewById( R.id.home_tab_mine );
        home_tab_inspiration_image=this.findViewById( R.id.home_tab_inspiration_button );
        home_tab_personal_image=this.findViewById( R.id.home_tab_personal_button );
        home_tab_color_image=this.findViewById( R.id.home_tab_color_button );
        home_tab_mine_image=this.findViewById( R.id.home_tab_mine_button );
        home_tab_inspiration_text=this.findViewById( R.id.home_tab_inspiration_text );
        home_tab_personal_text=this.findViewById( R.id.home_tab_personal_text );
        home_tab_color_text=this.findViewById( R.id.home_tab_color_text );
        home_tab_mine_text=this.findViewById( R.id.home_tab_mine_text );
        home_tab_inspiration.setOnClickListener( this );
        home_tab_personal.setOnClickListener( this );
        home_tab_color.setOnClickListener( this );
        home_tab_mine.setOnClickListener( this );
        home_tab_inspiration_image.setOnClickListener( this );
        home_tab_personal_image.setOnClickListener( this );
        home_tab_color_image.setOnClickListener( this );
        home_tab_mine_image.setOnClickListener( this );
        //实例化底栏

        //构造适配器
        List<Class<? extends Fragment>> fragments = new ArrayList<>();
        fragments.add( InspirationFragment.class );
        fragments.add( PersonalFragment.class );
        fragments.add( ColorFragment.class );
        fragments.add( MineFragment.class );
        FragmentPagerAdapter adapter = new HomeFragmentAdapter(this.getSupportFragmentManager(), fragments);

        homeViewPager=findViewById( R.id.home_viewpager );
        homeViewPager.setAdapter(adapter);
        homeViewPager.setOffscreenPageLimit(3);
        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            //页面选择事件
            public void onPageSelected(int position) {
                //设置对应集合中的Fragment
                homeViewPager.setCurrentItem(position);
                refreshTab();
                selectTab(position);
            }
            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) { }
        });
        homeViewPager.setCurrentItem(0);
        refreshTab();
        selectTab(0);
    }
    @Override
    public void onClick(View v) {
        int currentIndex = 0;
        refreshTab();
        switch (v.getId()) {
            case R.id.home_tab_inspiration_button:
            case R.id.home_tab_inspiration:
                currentIndex = 0;
                break;
            case R.id.home_tab_personal_button:
            case R.id.home_tab_personal:
                currentIndex = 1;
                break;
            case R.id.home_tab_color_button:
            case R.id.home_tab_color:
                currentIndex = 2;
                break;
            case R.id.home_tab_mine_button:
            case R.id.home_tab_mine:
                currentIndex = 3;
                break;
        }
        selectTab( currentIndex );
    }

    //将四个按钮变成灰色
    private void refreshTab() {
        home_tab_inspiration_image.setBackgroundResource( R.drawable.home_tab_inspiration );
        home_tab_personal_image.setBackgroundResource( R.drawable.home_tab_personal );
        home_tab_color_image.setBackgroundResource( R.drawable.home_tab_color );
        home_tab_mine_image.setBackgroundResource( R.drawable.home_tab_mine );
        home_tab_inspiration_text.setTextColor( getResources().getColor( R.color.home_tab_text_color ));
        home_tab_personal_text.setTextColor( getResources().getColor(R.color.home_tab_text_color ));
        home_tab_color_text.setTextColor( getResources().getColor(R.color.home_tab_text_color ));
        home_tab_mine_text.setTextColor( getResources().getColor(R.color.home_tab_text_color ));
    }

    //将点击的按钮设置选中态
    private void selectTab(int i) {
        switch (i) {
            case (0):
                home_tab_inspiration_image.setBackgroundResource( R.drawable.home_tab_inspiration_clicked );
                home_tab_inspiration_text.setTextColor( getResources().getColor(R.color.home_tab_text_color_clicked ));
                break;
            case (1):
                home_tab_personal_image.setBackgroundResource( R.drawable.home_tab_personal_clicked );
                home_tab_personal_text.setTextColor( getResources().getColor(R.color.home_tab_text_color_clicked ));
                break;
            case (2):
                home_tab_color_image.setBackgroundResource( R.drawable.home_tab_color_clicked );
                home_tab_color_text.setTextColor( getResources().getColor(R.color.home_tab_text_color_clicked ));
                break;
            case (3):
                home_tab_mine_image.setBackgroundResource( R.drawable.home_tab_mine_clicked );
                home_tab_mine_text.setTextColor( getResources().getColor(R.color.home_tab_text_color_clicked ));
                break;
        }
        //设置当前点击的Tab对应的界面.点击后切换当前选中fragment
        homeViewPager.setCurrentItem(i);
    }


    @Override
    public void onBackPressed() {
        if((System.currentTimeMillis() - exitTime) > 2000){
            Toast.makeText(this, "再次轻触退出应用", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else{
            startActivity(new Intent(this,ExitActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

}
