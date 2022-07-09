package com.zero.colorful.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.zero.colorful.R;
import com.zero.colorful.activity.MakeColorActivity;
import com.zero.colorful.adapter.GradientAdapter;
import com.zero.colorful.object.ColorHex;
import org.litepal.LitePal;
import java.util.List;

public class PersonalFragment extends Fragment {
    private QMUIEmptyView emptyView;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_personal, container, false );
        Toolbar toolbar=view.findViewById( R.id.toolbar );
        toolbar.setTitle( R.string.home_tab_personal );
        emptyView = view.findViewById( R.id.empty_view );
        recyclerView = view.findViewById( R.id.personal_recycler_view );
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        List<ColorHex> colorHexes = LitePal.findAll( ColorHex.class );
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        GradientAdapter adapter = new GradientAdapter(colorHexes);
        adapter.setOnItemClickListener( position -> {
            Intent intent = new Intent( getActivity(), MakeColorActivity.class );
            intent .putExtra( "colorPreview",colorHexes.get(position).getColorHex() );
            startActivity( intent );
        } );
        recyclerView.setAdapter(adapter);
        if (haveColor( colorHexes )) {
            recyclerView.setVisibility( View.GONE );
            emptyView.show( false, "您还没有保存任何颜色", null, "现在取一个色", v -> {
                Intent intent = new Intent( getActivity(), MakeColorActivity.class );
                startActivity( intent );
            } );
        }else {
            recyclerView.setVisibility( View.VISIBLE );
            emptyView.show( false );
        }
    }

    private boolean haveColor(List<ColorHex> colorHexes){
        for (ColorHex ignored :colorHexes){
            return false;
        }
        return true;
    }
}
