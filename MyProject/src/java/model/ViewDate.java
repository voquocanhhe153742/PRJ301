/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import helper.DateTimeHelper;
import java.util.Date;

/**
 *
 * @author admin
 */
public class ViewDate {
    private Date value;
    private int dow;

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }

    public int getDow() {
        return DateTimeHelper.getDayOfWeek(value);
    }

    
}
