package com.example.myworkout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;

/*
 * The TemplatesActivity class displays saved workout plan templates in a GridView.
 * It allows users to select and delete templates, and also to start a workout using a selected template.
 */

public class TemplatesActivity extends AppCompatActivity {

    GridView gridView;
    private ArrayList<WorkoutPlan> planList;
    int nPrevSelGridItem = -1;
    private Button templatebtn;
    TemplatesAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        planList = new ArrayList<>();
        templatebtn = findViewById(R.id.templatebtn);

        try {
            readInTemplates();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        gridAdapter = new TemplatesAdapter(this, planList);
        gridView = findViewById(R.id.templateGrid);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Open the context menu for the long-pressed item
                showWarningDialog(position);
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View viewPrev;

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    if (nPrevSelGridItem != -1) {
                        viewPrev = (View) gridView.getChildAt(nPrevSelGridItem);
                        viewPrev.setBackgroundColor(Color.WHITE);
                    }
                    nPrevSelGridItem = position;
                    if (nPrevSelGridItem == position) {
                        //View viewPrev = (View) gridview.getChildAt(nPrevSelGridItem);
                        view.setBackgroundColor(Color.parseColor("#B3E0F7"));
                    }
                    templatebtn.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        templatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Exercise> exList = new ArrayList<>();
                for (String s : planList.get(nPrevSelGridItem).getEx())
                {
                    exList.add(new Exercise(s));
                }
                Intent intent = new Intent(TemplatesActivity.this,WorkoutActivity.class);
                intent.putExtra("exerciseList", exList);
                startActivity(intent);
            }
        });

    }

    public void readInTemplates() throws FileNotFoundException {
        FileInputStream fis = this.openFileInput("workoutplans.csv");
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            reader.readLine();
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(",");

                DateTimeFormatter formatter = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    formatter = new DateTimeFormatterBuilder()
                            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS"))
                            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))
                            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            .appendOptional(DateTimeFormatter.ofPattern("d. MMMM yyyy"))
                            .appendOptional(DateTimeFormatter.ofPattern("HH:mm"))
                            .toFormatter();
                }

                LocalDateTime timestamp = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    System.out.println(data[0]);
                    timestamp = LocalDateTime.parse(data[0], formatter);
                }
                WorkoutPlan wp = new WorkoutPlan(timestamp,String.valueOf(data[1]), String.valueOf(data[2]),String.valueOf(data[3]));
                planList.add(wp);
                line = reader.readLine();
            }
            fis.close();
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        }
    }

    public void writeNewFile()
    {
        FileOutputStream fileout= null;
        try {
            fileout = openFileOutput("workoutplans.csv", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("timestamp,");
            outputWriter.write("time,");
            outputWriter.write("name,");
            outputWriter.write("exercises\n");
            for (WorkoutPlan wp : planList)
            {
                outputWriter.write(wp.getTimestamp() + ",");
                outputWriter.write(String.valueOf(wp.getTimestampTime())  + ",");
                outputWriter.write(String.valueOf(wp.getName()) + ",");
                outputWriter.write(String.valueOf(wp.getExerciseList()) + "\n");
            }
            outputWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showWarningDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Template")
                .setMessage("Would you like to delete the template called " + planList.get(position).getName())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        planList.remove(position);
                        gridAdapter.notifyDataSetChanged();
                        writeNewFile();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }


}