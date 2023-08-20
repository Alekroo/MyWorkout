package com.example.myworkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;

/*
 * The HistoryActivity class displays a history of workout plans in a RecyclerView.
 * It reads workout data from a CSV file, parses it, and populates the RecyclerView's adapter.
 */

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<WorkoutPlan> workoutList;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HistoryAdapter mAdapter;
    private String fileName = "exercises.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        workoutList = new ArrayList<>();
        try {
            readInFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        mRecyclerView = findViewById(R.id.historyRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new HistoryAdapter(this,workoutList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void readInFile() throws FileNotFoundException {
        FileInputStream fis = this.openFileInput("exercises.csv");
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            reader.readLine();
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(",");

                DateTimeFormatter formatter = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    formatter = new DateTimeFormatterBuilder()
                            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS"))
                            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))
                            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            .appendOptional(DateTimeFormatter.ofPattern("HH:mm"))
                            .toFormatter();
                }

                LocalDateTime timestamp = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    timestamp = LocalDateTime.parse(data[0], formatter);
                }

                //System.out.println(timestamp);
                WorkoutPlan wp = new WorkoutPlan(timestamp,String.valueOf(data[1]),"Workout",String.valueOf(data[2]),String.valueOf(data[3]));
                workoutList.add(wp);
                line = reader.readLine();
            }
            fis.close();
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        }
    }


}