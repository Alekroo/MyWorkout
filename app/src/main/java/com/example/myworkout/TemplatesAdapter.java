package com.example.myworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
 Designed to display workout plan templates in GrifView.
 */

public class TemplatesAdapter extends BaseAdapter {

    Context context;
    private ArrayList<WorkoutPlan> planList;
    LayoutInflater inflater;

    public TemplatesAdapter(Context context, ArrayList<WorkoutPlan> planList) {
        this.context = context;
        this.planList = planList;
    }

    @Override
    public int getCount() {
        return planList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null){
            view = inflater.inflate(R.layout.item_template,null);
        }

        TextView textView = view.findViewById(R.id.templateText);
        TextView imageView = view.findViewById(R.id.templateImage);

        textView.setText(planList.get(i).getName());

        String mString = "";
        for (String ex : planList.get(i).getEx())
        {
            mString = mString + "- " + ex + "\n";
            imageView.setText(mString);
        }
        return view;
    }




}
