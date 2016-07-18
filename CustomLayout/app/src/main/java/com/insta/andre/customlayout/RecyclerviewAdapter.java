package com.insta.andre.customlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.insta.andre.customlayout.layout.SquareLayout;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.CuadroViewHolder> {

    ArrayList<String> cuadros;
    Activity activity;

    public RecyclerviewAdapter(ArrayList<String> cuadros, Activity activity) {
        this.cuadros = cuadros;
        this.activity = activity;
    }

    @Override
    public CuadroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);

        return new CuadroViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return cuadros.size();
    }

    @Override
    public void onBindViewHolder(final CuadroViewHolder familiaViewHolder, int position) {
        final String cuadro = cuadros.get(position);

        familiaViewHolder.tvNombre.setText(cuadro);


    }


    public static class CuadroViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre;
        private SquareLayout squareContainer;

        public CuadroViewHolder(View itemView) {
            super(itemView);

            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            squareContainer = (SquareLayout) itemView.findViewById(R.id.squareContainer);
        }
    }
}
