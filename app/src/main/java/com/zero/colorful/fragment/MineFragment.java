package com.zero.colorful.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zero.colorful.R;
import com.zero.colorful.activity.AboutActivity;
import com.zero.colorful.activity.SettingsActivity;

public class MineFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_mine, container, false );
        Toolbar toolbar=view.findViewById( R.id.toolbar );
        toolbar.setTitle( R.string.home_tab_mine );
        View Setting=view.findViewById( R.id.fragment_setting_card );
        View About=view.findViewById( R.id.fragment_about_card );
        ImageView SettingImage=Setting.findViewById( R.id.card_image );
        ImageView AboutImage=About.findViewById( R.id.card_image );
        TextView SettingTitle=Setting.findViewById( R.id.card_title );
        TextView AboutTitle=About.findViewById( R.id.card_title );
        SettingImage.setBackground( getResources().getDrawable( R.drawable.setting ));
        AboutImage.setBackground( getResources().getDrawable( R.drawable.about ));
        SettingTitle.setText( R.string.setting );
        AboutTitle.setText( R.string.about );
        About.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about=new Intent( getActivity(), AboutActivity.class );
                startActivity( about );
            }
        } );
        Setting.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting=new Intent( getActivity(), SettingsActivity.class );
                startActivity( setting );
            }
        } );
        return view;
    }
}
