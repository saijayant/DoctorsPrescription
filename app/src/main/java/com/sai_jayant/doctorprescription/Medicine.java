package com.sai_jayant.doctorprescription;

/**
 * Created by Preet on 1/12/18.
 */

public class Medicine {

    Boolean isSelected;
    String medicine_name;
    String medicine_description;

    public String getMedicine_type() {
        return medicine_type;
    }

    public void setMedicine_type(String medicine_type) {
        this.medicine_type = medicine_type;
    }

    String medicine_type;

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getMedicine_description() {
        return medicine_description;
    }

    public void setMedicine_description(String medicine_description) {
        this.medicine_description = medicine_description;
    }


    public String getDaytime_after_food() {
        return daytime_after_food;
    }

    public void setDaytime_after_food(String daytime_after_food) {
        this.daytime_after_food = daytime_after_food;
    }

    public String getDaytime_before_food() {
        return daytime_before_food;
    }

    public void setDaytime_before_food(String daytime_before_food) {
        this.daytime_before_food = daytime_before_food;
    }

    public String getNighttime_before_food() {
        return nighttime_before_food;
    }

    public void setNighttime_before_food(String nighttime_before_food) {
        this.nighttime_before_food = nighttime_before_food;
    }

    public String getNighttime_after_food() {
        return nighttime_after_food;
    }

    public void setNighttime_after_food(String nighttime_after_food) {
        this.nighttime_after_food = nighttime_after_food;
    }

    String daytime_after_food;
    String daytime_before_food;
    String nighttime_before_food;
    String nighttime_after_food;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }




}
