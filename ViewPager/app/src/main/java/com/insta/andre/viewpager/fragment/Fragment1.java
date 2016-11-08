package com.insta.andre.viewpager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insta.andre.viewpager.R;

/**
 * Created by andre on 2/11/2016.
 */

public class Fragment1 extends Fragment {

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewPager vp=(ViewPager) getActivity().findViewById(R.id.viewpager);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1, container, false);

        return view;
    }
}
