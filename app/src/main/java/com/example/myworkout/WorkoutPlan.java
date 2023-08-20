package com.example.myworkout;

import android.os.Build;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Locale;

/*
 Class representing how a Workout-plan object should be.
 */

public class WorkoutPlan
{

    private String name;

    private LocalDateTime timestamp;
    private String time;
    private String exerciseList;
    private String performanceList;

    public String getExerciseList() {
        return exerciseList;
    }

    public WorkoutPlan(LocalDateTime timestamp, String time, String name, String exerciseList) {
        this.name = name;
        this.exerciseList = exerciseList;
        this.timestamp = timestamp;
        this.time = time;
    }

    public WorkoutPlan(LocalDateTime timestamp, String time, String name, String performanceList, String exerciseList ) {
        this.name = name;
        this.exerciseList = exerciseList;
        this.timestamp = timestamp;
        this.time = time;
        this.performanceList = performanceList;
    }

    public String[] getEx()
    {
        return exerciseList.split("_");
    }

    public String[][] getExercises()
    {


        String[] name =  exerciseList.split("_");

        String[] performance = performanceList.split("\\|");



        String[][] allList = new String[name.length][2];
        int j = 0;
        for(String x : name)
        {
            allList[j][0] = x;
            allList[j][1] = performance[j];


            j++;
        }
        System.out.println(" "+allList.length);

        return allList;
    }


    public String getName()
    {
        return name;
    }

    public String getTimestamp()
    {
        LocalDateTime timestamp2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             timestamp2 = LocalDateTime.parse(timestamp.toString(), DateTimeFormatter.ISO_DATE_TIME);
        }

        DateTimeFormatter customFormatter = null;
        // Define a custom date-time format without the "T"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        }

        String formattedTimestamp = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedTimestamp = timestamp2.format(customFormatter);
        }
        return formattedTimestamp;
    }
    public String getTimestampDate() {
        String timestamp2 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timestamp2 = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ENGLISH).format(timestamp);
        }
        return timestamp2;
    }

    public String getTimestampTime()
    {
        String t = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            t = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH).format(timestamp);
        }
        return t;
    }


}
