package com.example.myworkout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/*
 * The WorkoutChildAdapter class is responsible for populating a RecyclerView with child workout item views.
 * It displays the set number, weight input field, repetition input field, and a completion button for each set.
 */
public class WorkoutChildAdapter extends RecyclerView.Adapter<WorkoutChildAdapter.WorkoutChildViewHolder> {

    private ArrayList<String[]> performanceArray;
    private ArrayList<String> storedText;
    private WorkoutChildAdapter.OnItemClickListener mListener;
    private int number = 0;
    private String word = "bruh";

    public interface OnItemClickListener {
        void digitTyped(String kgData, String repdData, int position, int set, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public WorkoutChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_workout, parent, false);
        WorkoutChildViewHolder npa = new WorkoutChildViewHolder(v, mListener);
        return npa;
    }



    public WorkoutChildAdapter(ArrayList<String[]> performanceArray)
    {
        this.performanceArray = performanceArray;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutChildViewHolder holder, int position) {

        holder.setnrTV.setText("" + (position+1));
        String[] currentPerformance = performanceArray.get(position);
        holder.digitField1.setText(currentPerformance[0]);
        holder.digitField2.setText(currentPerformance[1]);
        if (currentPerformance[0] != null)
        {
            if (currentPerformance[0].isEmpty())
            {
                holder.completeBtn.setEnabled(true);
            }
            else {
                holder.completeBtn.setEnabled(false);
            }
        }
        else {
            holder.completeBtn.setEnabled(true);
        }



    }



    @Override
    public int getItemCount() {
        return performanceArray.size();
    }

    public static class WorkoutChildViewHolder extends RecyclerView.ViewHolder {
        public TextView setnrTV;
        public EditText digitField1;
        public EditText digitField2;
        public Button completeBtn;

        public WorkoutChildViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            setnrTV = itemView.findViewById(R.id.setnrTV);
            digitField1 = itemView.findViewById(R.id.digitField1);
            digitField2 = itemView.findViewById(R.id.digitField2);
            completeBtn = itemView.findViewById(R.id.completeBtn);

            completeBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (listener != null) {
                                int position = getAdapterPosition();
                                String kgData = digitField1.getText().toString();
                                String repdData = digitField2.getText().toString();
                                if (position != RecyclerView.NO_POSITION && !kgData.isEmpty() && !repdData.isEmpty()) {
                                    completeBtn.setEnabled(false);
                                    listener.digitTyped(kgData, repdData, position, 0, 3);
                                }
                            }
                        }
                    });

        }
    }

}
