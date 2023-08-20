package com.example.myworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
 * Adapter class to populate a RecyclerView with information from workout objects.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.PlanViewHolder> {

    private final Context context;
    private ArrayList<WorkoutPlan> historyList;
    private RecyclerView.LayoutManager mLayoutManager;

    HistoryChildAdapter cha;


    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        PlanViewHolder npa = new PlanViewHolder(v);
        return npa;
    }

    public HistoryAdapter(Context context, ArrayList<WorkoutPlan> historyList)
    {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        WorkoutPlan currentPlan = historyList.get(position);

        holder.mtextTitle.setText(currentPlan.getName());
        holder.mtextDate.setText("Date: " + currentPlan.getTimestampDate());
        holder.mtextTime.setText("Time: " + currentPlan.getTimestampTime());

        holder.childRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        cha = new HistoryChildAdapter(currentPlan.getExercises(), context);
        holder.childRecyclerView.setLayoutManager(mLayoutManager);
        holder.childRecyclerView.setAdapter(cha);

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder {
        public TextView mtextTitle;
        public TextView mtextDate;
        public TextView mtextTime;
        public TextView mtextExercises;
        public RecyclerView childRecyclerView;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            mtextTitle = itemView.findViewById(R.id.textTitle);
            mtextDate = itemView.findViewById(R.id.textDate);
            mtextTime = itemView.findViewById(R.id.textTime);
            mtextExercises = itemView.findViewById(R.id.textExercises);

                    childRecyclerView
                    = itemView
                    .findViewById(
                            R.id.secRecView);
        }
    }


}
