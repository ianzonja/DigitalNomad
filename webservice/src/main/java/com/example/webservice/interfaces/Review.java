package com.example.webservice.interfaces;

/**
 * Created by Davor on 15.1.2018..
 */

public class Review {
    public String name;

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String last_name;
    public float grade;
    public String review;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String comment) {
        this.review = review;
    }
}
