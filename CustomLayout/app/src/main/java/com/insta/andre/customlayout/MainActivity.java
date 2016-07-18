package com.insta.andre.customlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.insta.andre.customlayout.layout.SquareLayout;

public class MainActivity extends AppCompatActivity {

    SquareLayout squareContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        squareContainer = (SquareLayout) findViewById(R.id.squareContainer);

        squareContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerviewActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
