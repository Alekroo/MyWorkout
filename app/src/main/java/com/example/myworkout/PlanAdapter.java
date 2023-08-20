package com.example.myworkout;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapter class to populate a RecyclerView with a list of exercises and handle user interactions.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> implements Filterable {

    private ArrayList<Exercise> exercises;
    private ArrayList<Exercise> exercisesFull;
    private OnItemClickListener mListener;

    private static OnItemLongClickListener onItemLongClickListener; // Callback interface

    public interface OnItemLongClickListener {
        void onItemLongClick(int item);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);
        PlanViewHolder npa = new PlanViewHolder(v, mListener);
        return npa;
    }

    public PlanAdapter(ArrayList<Exercise> exercises)
    {
        this.exercises = exercises;
        exercisesFull = new ArrayList<>(exercises);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);

        holder.mImageView.setImageResource(currentExercise.getImage());
        holder.mTextView.setText(currentExercise.getName());
        holder.mTextView2.setText(currentExercise.getDescriptionMuscle());
        if(currentExercise.getSelected()){
            holder.rl2.setBackgroundColor(Color.parseColor("#B3E0F7"));
        }
        else{
            holder.rl2.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mImageView;
        public TextView mTextView;
        public TextView mTextView2;
        public RelativeLayout rl2;

        public PlanViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            rl2 = itemView.findViewById(R.id.rl2);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        // Notify the activity/fragment about the long-pressed item
                        if (onItemLongClickListener != null) {
                            onItemLongClickListener.onItemLongClick(position);
                        }
                        return true;
                    }
                    return false;
                }
            });
        }







}

    @Override
    public Filter getFilter() {
        return exerciseFilter;
    }

    private Filter exerciseFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Exercise> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exercisesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Exercise exercise : exercisesFull) {
                    if (exercise.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(exercise);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exercises.clear();
            exercises.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void armCategroy()
    {
        exercises.clear();
        for(Exercise e : exercisesFull)
        {
            if(e.getMuscle() == 2)
            {
                exercises.add(e);
            }
        }

        notifyDataSetChanged();
    }

    public void allCategroy()
    {
        exercises.clear();
        exercises.addAll(exercisesFull);
        notifyDataSetChanged();
    }

    public void chestCategory()
    {
        exercises.clear();
        for(Exercise e : exercisesFull)
        {
            if(e.getMuscle() == 0)
            {
                exercises.add(e);
            }
        }

        notifyDataSetChanged();
    }

    public void legCategory()
    {
        exercises.clear();
        for(Exercise e : exercisesFull)
        {
            if(e.getMuscle() == 1)
            {
                exercises.add(e);
            }
        }
        notifyDataSetChanged();
    }


}
