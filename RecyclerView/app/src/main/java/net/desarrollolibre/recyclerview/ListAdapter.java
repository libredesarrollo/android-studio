package net.desarrollolibre.recyclerview;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andres on 04/10/15.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private ArrayList<Person> persons;

    // Provee una referencia a cada item dentro de una vista y acceder a ellos facilmente
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Cada uno de los elementos de mi vista
        public TextView nameTextView,descriptionTextView;
        public CardView cardView;
        public LinearLayout colorLl;
        public RelativeLayout parentBodyRl;

        public ViewHolder(View v) {
            super(v);
            parentBodyRl = (RelativeLayout) v.findViewById(R.id.parent_body_rl);
            cardView = (CardView) v.findViewById(R.id.card_view);
            nameTextView = (TextView) v.findViewById(R.id.name_tv);
            descriptionTextView = (TextView) v.findViewById(R.id.description_tv);
            colorLl = (LinearLayout) v.findViewById(R.id.color_ll);
        }
    }

    // Constructor
    public ListAdapter(ArrayList<Person> persons) {
        this.persons = persons;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // inflo la vista (vista padre)
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter, parent, false);
        // creo el grupo de vistas
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Reemplaza en contenido de la vista
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.nameTextView.setText(persons.get(position).getName());
        viewHolder.descriptionTextView.setText(persons.get(position).getDescription());
        viewHolder.colorLl.setBackgroundColor(Color.parseColor(persons.get(position).getColor()));
    }

    // Retorna el tamano de nuestra data
    @Override
    public int getItemCount() {
        return persons.size();
    }

}