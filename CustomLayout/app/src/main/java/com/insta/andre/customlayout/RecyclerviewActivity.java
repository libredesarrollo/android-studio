package com.insta.andre.customlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerviewActivity extends AppCompatActivity {

    private ArrayList<String> cuadros;
    private RecyclerView rvData;
    RecyclerviewAdapter recyclerviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        cuadros = new ArrayList<>();

        cuadros.add("Uno");
        cuadros.add("Dos");
        cuadros.add("Tres");
        cuadros.add("Cuatro");
        cuadros.add("Cinco");
        cuadros.add("Seis");
        cuadros.add("Siete");

        rvData =  (RecyclerView) findViewById(R.id.rvData);

        recyclerviewAdapter = new RecyclerviewAdapter(cuadros, this);
        rvData.setAdapter(recyclerviewAdapter);
        GridLayoutManager glm = new GridLayoutManager(this,3);
        rvData.setLayoutManager(glm);
    }
}
