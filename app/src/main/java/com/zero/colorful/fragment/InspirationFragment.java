package com.zero.colorful.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zero.colorful.R;
import com.zero.colorful.adapter.HomeFragmentAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InspirationFragment extends Fragment implements View.OnClickListener{

    private ViewPager viewPager;
    private TextView inspiration_tab_1,inspiration_tab_2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_inspiration, container, false );
        inspiration_tab_1=view.findViewById( R.id.inspiration_tab_1 );
        inspiration_tab_2=view.findViewById( R.id.inspiration_tab_2 );
        viewPager=view.findViewById( R.id.inspiration_viewPager );
        List<Class<? extends Fragment>> fragments = new ArrayList<>();
        fragments.add( GradientFragment.class );
        fragments.add( CollocationFragment.class );
        FragmentPagerAdapter adapter = new HomeFragmentAdapter( Objects.requireNonNull( getActivity() ).getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            //页面选择事件
            public void onPageSelected(int position) {
                //设置对应集合中的Fragment
                viewPager.setCurrentItem(position);
                resetText();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
        resetText();
        selectTab(0);
        return view;
    }

    private void resetText() {
        inspiration_tab_1.setTextSize( TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.inspiration_tab_text)*1f);
        inspiration_tab_2.setTextSize( TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.inspiration_tab_text)*1f);
    }
    private void selectTab(int i) {
        switch (i) {
            case (0):
                inspiration_tab_1.setTextSize( TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.inspiration_tab_text_clicked)*1f);
                break;
            case (1):
                inspiration_tab_2.setTextSize( TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.inspiration_tab_text_clicked)*1f);
                break;
        }
        //设置当前点击的Tab对应的界面.点击后切换当前选中fragment
        viewPager.setCurrentItem(i);
    }

    @Override
    public void onClick(View v) {
        int currentIndex = 0;
        switch (v.getId()){
            case R.id.inspiration_tab_1:
                currentIndex = 0;
                break;
            case R.id.inspiration_tab_2:
                currentIndex = 1;
                break;
        }
        selectTab( currentIndex );
    }
}
