package com.example.myworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private Context context;
    private ArrayList<Exercise> exercises;
    private ArrayList<Integer> ex;
    private boolean btnEnabled;

    private OnItemClickListener mListener;
    private RecyclerView.LayoutManager mLayoutManager;
    WorkoutChildAdapter cha;

    public WorkoutAdapter(Context context, ArrayList<Exercise> exerciseList) {
        this.exercises = exerciseList;
        ex = new ArrayList<>();
        for (Exercise e : exerciseList)
        {
            ex.add(1);
        }
        this.context = context;
        btnEnabled = false;
    }

    public interface OnItemClickListener {
        void digitsTyped(String kgData, String repdData, int position, int set);
        void newSetClicked(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        WorkoutViewHolder npa = new WorkoutViewHolder(v, mListener);
        return npa;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        currentExercise.setHoldPosition(position);

        holder.addsetBtn.setEnabled(btnEnabled);
        holder.childRecyclerView.setItemViewCacheSize(currentExercise.getPerformanceArray().size());
        holder.titleText.setText(currentExercise.getName());
        mLayoutManager = new LinearLayoutManager(context);
        cha = new WorkoutChildAdapter(currentExercise.getPerformanceArray());
        holder.childRecyclerView.setLayoutManager(mLayoutManager);
        holder.childRecyclerView.setAdapter(cha);
        holder.titleText.setClickable(true);

        cha.setOnItemClickListener(new WorkoutChildAdapter.OnItemClickListener() {
            @Override
            public void digitTyped(String kgData, String repdData, int position, int set, int pos) {
                System.out.println(kgData + " - " + repdData);
                currentExercise.setPerformanceArray(position, kgData, repdData);
                cha.notifyItemChanged(position);
                holder.addsetBtn.setEnabled(true);



            }
        });

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder
    {
        public TextView titleText;
        public Button addsetBtn;
        public RecyclerView childRecyclerView;




        public WorkoutViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleText);
            addsetBtn = itemView.findViewById(R.id.addsetBtn);
            childRecyclerView = itemView.findViewById(R.id.workoutRV);



            addsetBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (listener != null) {
                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    listener.newSetClicked(position);

                                }
                            }
                        }
                    });
        }


    }

}
