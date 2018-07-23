package jv.dl.vuforia.wikitude.util.adapter;

import jv.dl.vuforia.wikitude.R;
import jv.dl.vuforia.wikitude.util.SampleCategory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;


public class SamplesExpendableListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<SampleCategory> sampleCategories;

    public SamplesExpendableListAdapter(@NonNull Context context, @NonNull List<SampleCategory> sampleCategories) {
        this.context = context;
        this.sampleCategories = sampleCategories;
    }

    @Override
    public int getGroupCount() {
        return sampleCategories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sampleCategories.get(groupPosition).getSamples().length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sampleCategories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return sampleCategories.get(groupPosition).getSamples()[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean expanded, View view, ViewGroup viewGroup) {


        if (view == null) {
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expand_header, null);
        }

        final String categoryTitle = sampleCategories.get(groupPosition).getName();

        final TextView categoryText = view.findViewById(R.id.expand_text);
        categoryText.setText(categoryTitle);

        if (expanded) {
            view.setBackgroundColor(context.getResources().getColor(R.color.wikitude_primary));
        } else {
            view.setBackgroundColor(context.getResources().getColor(R.color.wikitude_white));
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean lastChild, View view, ViewGroup viewGroup) {

        final String sampleName = (String) getChild(groupPosition, childPosition);

        if (view == null) {
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expand_row, null);
        }

        final TextView sampleText = view.findViewById(R.id.expand_row_text);
        sampleText.setText(sampleName);

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
