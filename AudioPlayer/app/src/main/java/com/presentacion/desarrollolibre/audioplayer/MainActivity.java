package com.presentacion.desarrollolibre.audioplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeekBarView seekBarView = new SeekBarView(MainActivity.this);
        FrameLayout.LayoutParams myFrameLayoutParams = new FrameLayout.LayoutParams(300,300);
        seekBarView.setLayoutParams(myFrameLayoutParams);
        setContentView(seekBarView);


        seekBarView.setVisibility(View.VISIBLE);
    }


}
