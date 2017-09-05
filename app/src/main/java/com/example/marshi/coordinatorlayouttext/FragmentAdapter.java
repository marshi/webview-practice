package com.example.marshi.coordinatorlayouttext;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private LayoutInflater inflater;

    public FragmentAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Fragment getItem(int position) {
        return WebViewFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
