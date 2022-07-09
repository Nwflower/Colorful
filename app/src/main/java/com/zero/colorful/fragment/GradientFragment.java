package com.zero.colorful.fragment;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zero.colorful.R;
import com.zero.colorful.activity.GradientActivity;
import com.zero.colorful.adapter.GradientAdapter;
import com.zero.colorful.object.ColorHex;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GradientFragment extends Fragment {
    private List<ColorHex> GradientList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_gradient, container, false );

        Resources res = super.getResources();					// 操作资源
        InputStream input = res.openRawResource(R.raw.gradientcolor);	// 读取资源ID
        Scanner scan = new Scanner(input);						// 实例化Scanner
        StringBuilder buf = new StringBuilder();					// 接收数据
        while (scan.hasNext()) {								// 循环读取
            buf.append(scan.next()).append("\n");				// 保存数据
        }
        scan.close();											// 关闭输入流
        try {													// 关闭输入流
            input.close() ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string=buf.toString();
        final String[] gradient = string.split( "\n" );
        for (int x=0;x<gradient.length;x++){
            ColorHex colorHex = new ColorHex("", gradient[x]);
            GradientList.add(colorHex);
        }

        RecyclerView recyclerView=view.findViewById( R.id.gradient_recycler_view );
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        GradientAdapter adapter = new GradientAdapter(GradientList);
        adapter.setOnItemClickListener( position -> {
            Intent intent = new Intent( getActivity(), GradientActivity.class );
            intent .putExtra( "color",GradientList.get(position).getColorHex() );
            startActivity( intent );
        } );
        recyclerView.setAdapter(adapter);
        return view;
    }
}
