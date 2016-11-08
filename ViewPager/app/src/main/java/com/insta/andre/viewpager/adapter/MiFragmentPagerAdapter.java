package com.insta.andre.viewpager.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.insta.andre.viewpager.fragment.Fragment1;
import com.insta.andre.viewpager.fragment.Fragment2;

import java.util.ArrayList;

/**
 * Created by andre on 2/11/2016.
 */


public class MiFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> tags;

    public MiFragmentPagerAdapter(FragmentManager fm, ArrayList<String> tags, Activity activity) {
        super(fm);
        this.tags = tags;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;

        switch(position) {
            case 0:
                f = new Fragment1(); //1
                break;
            case 1:
                f = new Fragment2(); //2
                break;
            case 2:
                f = new Fragment1();//v1
                break;
        }

        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tags.get(position);
    }
}