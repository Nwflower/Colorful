package com.zero.colorful.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDrawableHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zero.colorful.R;
import com.zero.colorful.activity.ImageActivity;
import com.zero.colorful.activity.MakeColorActivity;

import java.util.Objects;

public class ColorFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.fragment_color, container, false );
        Toolbar toolbar=view.findViewById( R.id.toolbar );
        toolbar.setTitle( R.string.home_tab_color );

        View MakeColor=view.findViewById( R.id.makeColor );
        View GetColor=view.findViewById( R.id.getColor );
        View TakeColor=view.findViewById( R.id.takeColor );
        ConstraintLayout MakeColorCard=MakeColor.findViewById( R.id.color_card );
        ConstraintLayout GetColorCard=GetColor.findViewById( R.id.color_card );
        ConstraintLayout TakeColorCard=TakeColor.findViewById( R.id.color_card );


        TextView MakeColorTitle = MakeColor.findViewById( R.id.color_card_title );
        TextView MakeColorText = MakeColor.findViewById( R.id.color_card_text );
        ImageView MakeColorImage = MakeColor.findViewById( R.id.color_card_image );
        TextView GetColorTitle = GetColor.findViewById( R.id.color_card_title );
        TextView GetColorText = GetColor.findViewById( R.id.color_card_text );
        ImageView GetColorImage = GetColor.findViewById( R.id.color_card_image );
        TextView TakeColorTitle = TakeColor.findViewById( R.id.color_card_title );
        TextView TakeColorText = TakeColor.findViewById( R.id.color_card_text );
        ImageView TakeColorImage = TakeColor.findViewById( R.id.color_card_image );
        MakeColorTitle.setText( R.string.MakeColorTitle );
        GetColorTitle.setText( R.string.GetColorTitle );
        TakeColorTitle.setText( R.string.TakeColorTitle );
        MakeColorText.setText( R.string.MakeColorText );
        GetColorText.setText( R.string.GetColorText );
        TakeColorText.setText( R.string.TakeColorText );

        Drawable drawable1 = getResources().getDrawable( R.drawable.color_circle );
        Drawable drawable2 = getResources().getDrawable( R.drawable.color_circle );
        Drawable drawable3 = getResources().getDrawable( R.drawable.color_circle );
        // 其中一张重新设置颜色
        QMUIDrawableHelper.setDrawableTintColor(drawable1, ContextCompat.getColor( Objects.requireNonNull( getContext() ), R.color.color_circle1));
        MakeColorImage.setImageDrawable(drawable1);
        QMUIDrawableHelper.setDrawableTintColor(drawable2, ContextCompat.getColor( Objects.requireNonNull( getContext() ), R.color.color_circle2));
        GetColorImage.setImageDrawable(drawable2);
        QMUIDrawableHelper.setDrawableTintColor(drawable3, ContextCompat.getColor( Objects.requireNonNull( getContext() ), R.color.color_circle3));
        TakeColorImage.setImageDrawable(drawable3);
        MakeColorCard.setOnClickListener( v -> {
            Intent intent=new Intent( getActivity(), MakeColorActivity.class );
            startActivity( intent );
        } );
        GetColorCard.setOnClickListener( v -> {
            Intent intent=new Intent( getActivity(), ImageActivity.class );
            startActivity( intent );
        } );
        TakeColorCard.setOnClickListener( v -> {
            final QMUITipDialog tipDialog;
            tipDialog = new QMUITipDialog.Builder(v.getContext())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                    .setTipWord("功能维护中")
                    .create();
            tipDialog.show();
            v.postDelayed( tipDialog::dismiss, 1000);
        } );
        return view;
    }
}
