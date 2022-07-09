package com.zero.colorful.activity;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;

import com.zero.colorful.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.settings_activity );
        Toolbar toolbar = this.findViewById( R.id.toolbar );
        toolbar.setTitle( R.string.setting );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );
        @SuppressLint("PrivateResource") final Drawable upArrow = getResources().getDrawable( R.drawable.abc_ic_ab_back_material );
        upArrow.setColorFilter( getResources().getColor( R.color.text_color_in_toolBar_back ), PorterDuff.Mode.SRC_ATOP );
        getSupportActionBar().setHomeAsUpIndicator( upArrow );
        getSupportFragmentManager()
                .beginTransaction()
                .replace( R.id.settings, new SettingsFragment() )
                .commit();


    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource( R.xml.root_preferences, rootKey );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}