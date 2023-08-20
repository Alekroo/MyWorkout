package com.example.myworkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;

/*
 * PlanActivity.java
 * This class is meant for creating workout plans.
 * It allows users to select exercises from different categories and create custom workout plans.
 * Users can view exercise details, mark exercises as selected, and proceed to the next step.
 * The selected exercises are passed to the WorkoutActivity for further processing.
 */

public class PlanActivity extends AppCompatActivity {

    private ArrayList<Exercise> exerciseList;
    private ArrayList<Exercise> selectedExercises;
    private RecyclerView mRecyclerView;
    private PlanAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int selectedNumberCounter;
    private Button next_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        selectedNumberCounter = 0;
        next_button = findViewById(R.id.next_button);
        mySpinner();

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PlanActivity.this,WorkoutActivity.class);
                intent.putExtra("exerciseList", selectedExercises);
                startActivity(intent);
            }
        });
    }

    public void changeItem(int position) {
        exerciseList.get(position).setSelected(!exerciseList.get(position).getSelected());
        mAdapter.notifyItemChanged(position);
    }

    private void setUpData()
    {

        exerciseList = new ArrayList<>();
        Exercise benchpress = new Exercise("Benchpress", "Lie on a bench, grip the " +
                "barbell slightly wider than shoulder-width, lower it to your chest, " +
                "then push it back up while keeping your back, butt, and head on the bench. " +
                "Use proper form to engage chest, shoulders, and triceps for an effective bench press.", 0);
        benchpress.setImage(R.drawable.benchpress);
        exerciseList.add(benchpress);

        Exercise ibenchpress = new Exercise("Incline-Benchpress", "Lie back on an " +
                "inclined bench set at around a 45-degree angle, holding a barbell or dumbbells at " +
                "shoulder width. Lower the weights to your chest, then press them back up, engaging " +
                "your chest and shoulders while maintaining proper form and control.", 0);
        ibenchpress.setImage(R.drawable.incline_benchpress);
        exerciseList.add(ibenchpress);

        Exercise treadmill = new Exercise("Treadmill", "\n" +
                "Stand on the treadmill, select your desired speed and incline level, then start " +
                "walking or running while keeping a steady and balanced stride. To stop, reduce the " +
                "speed gradually and step off the treadmill carefully.", 1);
        treadmill.setImage(R.drawable.treadmill);
        exerciseList.add(treadmill);

        Exercise leg_curl = new Exercise("Leg-Curl", "Lie face down on a leg curl " +
                "machine, aligning your ankles with the padded lever. Curl your legs upward by " +
                "bending your knees, contracting your hamstrings, then lower the lever back down " +
                "in a controlled manner to complete one repetition.", 1);
        leg_curl.setImage(R.drawable.leg_curl);
        exerciseList.add(leg_curl);

        Exercise leg_press = new Exercise("Leg-Press", "Sit on a leg press machine, " +
                "placing your feet shoulder-width apart on the platform. Push the platform away by " +
                "extending your legs, then lower it back down by bending your knees, keeping your " +
                "back against the seat and maintaining a controlled motion throughout.", 1);
        leg_press.setImage(R.drawable.leg_press);
        exerciseList.add(leg_press);

        Exercise hammer_curl = new Exercise("Hammer-Curl", "\n" +
                "Hold a dumbbell in each hand with your palms facing your body. Keeping your upper" +
                " arms stationary, curl the dumbbells while contracting your biceps, then lower" +
                " them back down in a controlled manner, maintaining a neutral wrist position" +
                " throughout the movement.", 2);
        hammer_curl.setImage(R.drawable.hammer_curl);
        exerciseList.add(hammer_curl);

        Exercise curl = new Exercise("Curl", "Hold a barbell or dumbbells with an" +
                " underhand grip, arms fully extended. Keeping your upper arms stationary, " +
                "curl the weight while contracting your biceps, then lower it back down in a " +
                "controlled manner, maintaining proper form and avoiding swinging motions.", 2);
        curl.setImage(R.drawable.curl);
        exerciseList.add(curl);

        Exercise triceps_extension = new Exercise("Triceps Extension", "\n" +
                "Attach a rope handle to a high pulley on a cable machine. Stand facing the machine," +
                " hold the rope with an overhand grip, and extend your arms downward while keeping" +
                " your elbows close to your body, then return to the starting position " +
                "by contracting your triceps.", 2);
        triceps_extension.setImage(R.drawable.triceps_extension);
        exerciseList.add(triceps_extension);
        selectedExercises = new ArrayList<>();

        mRecyclerView = findViewById(R.id.planRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PlanAdapter(exerciseList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new PlanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position);
                if(exerciseList.get(position).getSelected()){
                    selectedExercises.add(exerciseList.get(position));
                }
                else{
                    if (selectedExercises.contains(exerciseList.get(position))) {
                        selectedExercises.remove(exerciseList.get(position));
                    }
                }
            }
        });

        mAdapter.setOnItemLongClickListener(new PlanAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int item) {
                showWarningDialog(exerciseList.get(item));
            }
        });
    }

    private void showWarningDialog(Exercise exercise) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(exercise.getName())
                .setMessage(exercise.getDescription())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform action when OK button is clicked
                    }
                })
                .setIcon(exercise.getImage())
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setUpData();

        SearchView searchView = findViewById(R.id.searchbar);

        searchView.setIconifiedByDefault(false);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public void mySpinner()
    {
        Spinner spinner = (Spinner) findViewById(R.id.exercise_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercise_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0) {
                    mAdapter.allCategroy();
                } else if (position == 1) {
                    mAdapter.armCategroy();
                } else if (position == 2) {
                    mAdapter.chestCategory();
                } else if (position == 3) {
                    mAdapter.legCategory();
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }

}