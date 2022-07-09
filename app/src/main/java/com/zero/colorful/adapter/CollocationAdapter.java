package com.zero.colorful.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qmuiteam.qmui.widget.QMUIVerticalTextView;
import com.zero.colorful.R;
import com.zero.colorful.object.ColorHex;

import java.text.DecimalFormat;
import java.util.List;

public class CollocationAdapter extends RecyclerView.Adapter<CollocationAdapter.ViewHolder> {

    private List<ColorHex> mCollocationList;
    // 利用接口 -> 给RecyclerView设置点击事件
    private ItemClickListener mItemClickListener ;
    public interface ItemClickListener{
        public void onItemClick(int position) ;
    }
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView colorName,colorHexText;
        QMUIVerticalTextView colorNumber;
        LinearLayout lineColor;
        public ViewHolder(View view) {
            super( view );
            colorName= view.findViewById( R.id.colorName );
            colorHexText= view.findViewById( R.id.color_hex );
            colorNumber=view.findViewById( R.id.color_number );
            lineColor=view.findViewById( R.id.line_color );
        }

    }

    public CollocationAdapter(List<ColorHex> CollocationList) {
        mCollocationList = CollocationList;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.collocation_item, parent, false );
        ViewHolder holder = new ViewHolder( view );
        return holder;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ColorHex colorHex=mCollocationList.get( position );
        holder.colorName.setText( colorHex.getName() );
        DecimalFormat df=new DecimalFormat("000");
        String number=df.format(position+1);
        holder.colorNumber.setText( number );
        holder.colorHexText.setText( colorHex.getColorHex() );
        holder.lineColor.setBackgroundColor( Color.parseColor( colorHex.getColorHex().toUpperCase() ) );
        // 点击事件一般都写在绑定数据这里，当然写到上边的创建布局时候也是可以的
        if (mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 这里利用回调来给RecyclerView设置点击事件
                    mItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCollocationList.size();
    }
}