package com.example.myworkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/*
 * MainActivity class serves as the entry point for the MyWorkout app.
 * It handles the main screen of the app where users can access different sections.
 * The main functionalities include creating necessary CSV files if they don't exist
 * and setting up click listeners for the image buttons to navigate to other activities.
 */

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    private String workoutFile = "workoutplans.csv";
    private String fileName = "exercises.csv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton1 = findViewById(R.id.image_button1);
        imageButton2 = findViewById(R.id.image_button2);
        imageButton3 = findViewById(R.id.image_button3);

        createFiles();

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlanActivity.class));
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TemplatesActivity.class));
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });

    }

    public void createFiles()
    {
        if(!fileExist(workoutFile))
        {
                FileOutputStream fileout= null;
                try {
                    fileout = openFileOutput("workoutplans.csv", MODE_APPEND);
                    OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                    outputWriter.write("timestamp,time,name,exercises\n");
                    outputWriter.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        if (!fileExist(fileName))
        {
            FileOutputStream fileout= null;
            try {
                fileout = openFileOutput("exercises.csv", MODE_APPEND);
                OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                outputWriter.write("date,");
                outputWriter.write("timestamp,");
                outputWriter.write("time,");
                outputWriter.write("performance,");
                outputWriter.write("exercises\n");
                outputWriter.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}