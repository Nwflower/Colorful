package com.zero.colorful.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private List<Class<? extends Fragment>> fragmentClasses;
    private List<Fragment> mFragments;

    public HomeFragmentAdapter(@NonNull FragmentManager fm,List<Class<? extends Fragment>> fragmentClasses) {
        super( fm );
        this.fragmentClasses = fragmentClasses;
        this.mFragments = new ArrayList<Fragment>( Collections.<Fragment>nCopies(fragmentClasses.size(), null));
    }


    @Override
    public Fragment getItem(int arg0) {
        if (mFragments.get(arg0) == null){
            Fragment newlyCreateFragment = null;
            try {
                newlyCreateFragment = fragmentClasses.get(arg0).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            mFragments.set(arg0, newlyCreateFragment);
        }
        return mFragments.get( arg0 );
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
