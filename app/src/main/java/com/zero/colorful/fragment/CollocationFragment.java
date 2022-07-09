package com.zero.colorful.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.CaseMap;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zero.colorful.R;
import com.zero.colorful.activity.collocationActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class CollocationFragment extends Fragment implements View.OnClickListener{
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_collocation, container, false );
        ConstraintLayout chineseColorBack=view.findViewById( R.id.chinese_card );
        String[] gradient = {"F44336","FFA726"};
        int[] color=new int[gradient.length];
        for (int x=0;x<gradient.length;x++){
            int a= Color.parseColor( "#"+gradient[x] );
            color[x]=a;
        }
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,color);
        drawable.setCornerRadius(10);
        drawable.setGradientType( GradientDrawable.RECTANGLE);
        chineseColorBack.setBackground(drawable);

        TextView ChineseTitle=view.findViewById( R.id.card_title );
        ChineseTitle.setText( R.string.ChineseColorTitle );
        TextView ChineseText=view.findViewById( R.id.card_text );
        ChineseText.setText( R.string.ChineseColorText );
        chineseColorBack.setOnClickListener( this );

        ConstraintLayout japanColorBack=view.findViewById( R.id.japan_card );
        String[] gradient2 = {"799179","e198b4"};
        int[] color2=new int[gradient2.length];
        for (int x=0;x<gradient2.length;x++){
            int a= Color.parseColor( "#"+gradient2[x] );
            color2[x]=a;
        }
        GradientDrawable drawable2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,color2);
        drawable2.setCornerRadius(10);
        drawable2.setGradientType( GradientDrawable.RECTANGLE);
        japanColorBack.setBackground(drawable2);

        TextView japanTitle=view.findViewById( R.id.japan_card_title );
        japanTitle.setText( R.string.JapanColorTitle );
        TextView japanText=view.findViewById( R.id.japan_card_text );
        japanText.setText( R.string.JapanColorText );
        japanColorBack.setOnClickListener( this );
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chinese_card:
                Intent  intent=new Intent( getActivity(), collocationActivity.class );
                intent.putExtra( "color",R.raw.chinesecolor );
                intent.putExtra( "title","中国传统配色" );
                Objects.requireNonNull( getActivity() ).startActivity( intent );
                break;
            case R.id.japan_card:
                Intent  intent2=new Intent( getActivity(), collocationActivity.class );
                intent2.putExtra( "color",R.raw.japancolor );
                intent2.putExtra( "title","日本传统配色" );
                Objects.requireNonNull( getActivity() ).startActivity( intent2 );
                break;
        }
    }
}
