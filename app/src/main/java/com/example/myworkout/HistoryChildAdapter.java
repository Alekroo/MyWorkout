package com.example.myworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
/*
 * The HistoryChildAdapter class is responsible for populating a RecyclerView with child history item views.
 * It displays exercise details and performance data within each history entry.
 */
public class HistoryChildAdapter extends RecyclerView.Adapter<HistoryChildAdapter.ChildHistoryViewHolder> {

    private Context context;
    private String[][] exercises;

    @NonNull
    @Override
    public ChildHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_history, parent, false);
        ChildHistoryViewHolder npa = new ChildHistoryViewHolder(v);
        return npa;
    }

    public HistoryChildAdapter(String[][] exercises, Context context)
    {
        this.exercises = exercises;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ChildHistoryViewHolder holder, int position) {

        String rc = exercises[position][0];

        holder.mtextTitle.setText(rc);
        String allPer = exercises[position][1];
        String[] tmpPer = allPer.split("\\.");


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.weight = 1;

        for(int i = 1; i < tmpPer.length; i=i+2)
        {
            TextView tv = new TextView(context);
            tv.setText(tmpPer[i]+"kg x "+tmpPer[i+1]);
            tv.setLayoutParams(layoutParams);
            holder.myLL.addView(tv);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.length;
    }

    public static class ChildHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView mtextTitle;

        public LinearLayout myLL;

        public ChildHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mtextTitle = itemView.findViewById(R.id.textView4);
            myLL = itemView.findViewById(R.id.heill);
        }
    }

}
