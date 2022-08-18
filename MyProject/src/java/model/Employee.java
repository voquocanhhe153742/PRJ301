/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Employee {
    private int id;
    private String name;
    private float salary;
    private ArrayList<TimeSheet> timesheets = new ArrayList<>();

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
    
    public float getWorkingHours()
    {
        float sum = 0;
        for (TimeSheet timesheet : timesheets) {
            sum += timesheet.getWorkingHours();
        }
        return sum;
    }
    
    public int getWorkingDays()
    {
        return timesheets.size();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TimeSheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(ArrayList<TimeSheet> timesheets) {
        this.timesheets = timesheets;
    }
    
    
}
