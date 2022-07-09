package com.zero.colorful.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zero.colorful.R;
import com.zero.colorful.object.ColorHex;

import java.util.List;

public class GradientAdapter extends RecyclerView.Adapter<GradientAdapter.ViewHolder> {

    private List<ColorHex> mGradientList;
    // 利用接口 -> 给RecyclerView设置点击事件
    private ItemClickListener mItemClickListener ;
    public interface ItemClickListener{
        void onItemClick(int position) ;
    }
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout colorPreview;

        ViewHolder(View view) {
            super( view );
            colorPreview =  view.findViewById( R.id.colorPreview );
        }

    }

    public GradientAdapter(List<ColorHex> GradientList) {
        mGradientList = GradientList;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.gradient_item, parent, false );
        return new ViewHolder( view );
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ColorHex colorHex = mGradientList.get( position );
        String[] gradient = colorHex.getColorHex().split( "#" );
        if (gradient.length <= 2 ) {
            holder.colorPreview.setBackgroundColor( Color.parseColor( colorHex.getColorHex() ) );
        }else {
            int[] color = new int[gradient.length - 1];
            for (int x = 1; x < gradient.length; x++) {
                int a = Color.parseColor( "#" + gradient[x] );
                color[x - 1] = a;
            }
            GradientDrawable drawable = new GradientDrawable( GradientDrawable.Orientation.LEFT_RIGHT, color );
            drawable.setCornerRadius( 10 );
            drawable.setGradientType( GradientDrawable.RECTANGLE );
            holder.colorPreview.setBackground( drawable );
        }
        // 点击事件一般都写在绑定数据这里，当然写到上边的创建布局时候也是可以的
        if (mItemClickListener != null){
            holder.itemView.setOnClickListener( v -> {
                // 这里利用回调来给RecyclerView设置点击事件
                mItemClickListener.onItemClick(position);
            } );
        }
    }

    @Override
    public int getItemCount() {
        return mGradientList.size();
    }
}