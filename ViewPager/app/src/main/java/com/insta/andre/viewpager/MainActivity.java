package com.insta.andre.viewpager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.insta.andre.viewpager.adapter.MiFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by andre on 2/11/2016.
 */

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    ArrayList<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        final int[] ICONS = new int[]{
                R.drawable.ic_action_android,
                R.drawable.ic_action_bug_report,
                R.drawable.ic_action_favorite
        };

        tags = new ArrayList<>();

        tags.add("Uno");
        tags.add("Dos");
        tags.add("Tres");

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager.setAdapter(new MiFragmentPagerAdapter(
                getSupportFragmentManager(), tags, MainActivity.this));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {

        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //  tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }
        };
    }
}