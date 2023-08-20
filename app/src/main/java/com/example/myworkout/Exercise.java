package com.example.myworkout;

import java.io.Serializable;
import java.util.ArrayList;

/*
    Class representing an exercise object.
 */

public class Exercise implements Serializable {

    private boolean selected;
    private String name;
    private String description;
    private int muscle;
    private int selectedNumber;
    private ArrayList<String[]> performanceArray;
    private int holdPosition;

    public Exercise(String name, String description, int muscle)
    {
        this.name = name;
        this.description = description;
        this.muscle = muscle;
        this.selected = false;
    }
    public void setHoldPosition(int holdPosition) {
        this.holdPosition = holdPosition;
    }
    private int image = R.drawable.baseline_accessibility_24;
    public int getMuscle() {
        return muscle;
    }

    public void setImage(int i){
        this.image = i;
    }

    public Exercise(String name)
    {
        this.name = name;
        this.selectedNumber = 0;
    }

    public String getName()
    {
        return name;
    }

    public int getImage()
    {
        return image;
    }

    public String getDescription()
    {

        return "Primary muscle: " + getDescriptionMuscle() + "\n\n" + description;
    }

    public String getDescriptionMuscle()
    {
        String m;
        if(muscle == 0)
        {
            m = "Chest";
        }
        else if(muscle == 1)
        {
            m = "Legs";
        }
        else {
            m = "Arms";
        }
        return m;
    }

    public Boolean getSelected() {
        return selected;
    }
    public void setSelected(boolean s) {selected = s;};

    public void setPerformanceArray(int set, String kg_data, String rep_data)
    {
        performanceArray.get(set)[0]=kg_data;
        performanceArray.get(set)[1]=rep_data;
    }

    public void addPerformanceArray()
    {
        performanceArray.add(new String[2]);
    }

    public ArrayList<String[]> getPerformanceArray() {
        if(performanceArray == null)
        {
            performanceArray = new ArrayList<>();
            performanceArray.add(new String[2]);
        }
        return performanceArray;
    }

}
