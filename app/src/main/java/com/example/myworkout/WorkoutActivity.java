package com.example.myworkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;

/*
 * The WorkoutActivity class manages the workout session, recording exercises' performance,
 * and providing options to save workout data and create workout templates.
 */

public class WorkoutActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private WorkoutAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    private ArrayList<Exercise> exerciseList;
    private String fileName = "exercises.csv";
    private String timeLasted = "22 min";
    private Button button2;
    private String allExercises = "";
    private String allPerformace = "";
    private Timestamp timestamp2;
    private  Timestamp timestamp1;
    private LocalDateTime now;
    private Button button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        this.context = this;

        Date date = new Date();
        timestamp1 = new Timestamp(date.getTime());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        exerciseList = (ArrayList<Exercise>) getIntent().getSerializableExtra("exerciseList");

        mRecyclerView = findViewById(R.id.workoutRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new WorkoutAdapter(this,exerciseList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        mAdapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void digitsTyped(String kgData, String repdData, int position, int set) {
                exerciseList.get(position).setPerformanceArray(set, kgData, repdData);
                Toast.makeText(getApplicationContext(), kgData + " " + repdData, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void newSetClicked(int position) {
                exerciseList.get(position).addPerformanceArray();
                mAdapter.notifyItemChanged(position);
            }
        });

        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveToFile();
                        showWarningDialog1();
                    }
                });

        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(WorkoutActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
    }
    public void saveToFile()
    {

        try {
            Date date2 = new Date();
            timestamp2 = new Timestamp(date2.getTime());
            FileOutputStream fileout=openFileOutput(fileName, MODE_APPEND);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(String.valueOf(timestamp2) + ",");
            outputWriter.write(String.valueOf("22 min") + ",");
            for(Exercise ex : exerciseList) {
                allExercises = allExercises + ex.getName() + "_";

                for(String[] perf : ex.getPerformanceArray())
                {

                    allPerformace = allPerformace+ "." + perf[0] + "." + perf[1];
                }

                allPerformace = allPerformace + "|";
                System.out.println(allPerformace);
            }
            allPerformace = allPerformace.substring(0, allPerformace.length() - 1);
            outputWriter.write(allPerformace + ",");
            allExercises = allExercises.substring(0, allExercises.length() - 1);
            outputWriter.write(allExercises + "\n");
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void showWarningDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_createtemplate, null))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ..
                        EditText editText = ((AlertDialog) dialog).findViewById(R.id.templatename1);
                        String enteredText = editText.getText().toString();
                        saveWorkoutTemplate(enteredText);
                        Intent intent = new Intent(WorkoutActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(WorkoutActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();

    }

    public void saveWorkoutTemplate(String templatename)
    {
        FileOutputStream fileout= null;
        try {
            fileout = openFileOutput("workoutplans.csv", MODE_APPEND);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(String.valueOf(timestamp2) + ",");
            outputWriter.write(String.valueOf(timeLasted) + ",");
            outputWriter.write(String.valueOf(getWorkoutName(templatename)) + ",");
            String fullExercisePlan = "";

            for(Exercise ex : exerciseList)
            {
                fullExercisePlan = fullExercisePlan + ex.getName() + "_";
            }
            fullExercisePlan = fullExercisePlan.substring(0, fullExercisePlan.length() - 1);
            outputWriter.write(fullExercisePlan + "\n");
            outputWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getWorkoutName(String templatename)
    {
        if(templatename.length()>0)
        {
            return templatename;
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime now = LocalDateTime.now();
            return "Workout " + formatter.format(now);
        }
        return "error";
    }

}