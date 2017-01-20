package com.example.kemo.socializer.View.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.kemo.socializer.View.MainActivityFragment;

import java.util.ArrayList;

/**
 * Created by kemo on 07/01/2017.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    public ArrayList<MainActivityFragment> getFragments() {
        return fragments;
    }

    //TODO implement later
    private ArrayList<MainActivityFragment> fragments;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getFragTitle();
    }
}
