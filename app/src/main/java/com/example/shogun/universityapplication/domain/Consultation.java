package com.example.shogun.universityapplication.domain;

import java.util.HashSet;
import java.util.Set;
/**
 * Created by Krystian on 2016-11-12.
 */

public class Consultation{


    private String dateTime;


    private Boolean cancelled;


    private User teacher;


    private Set<String> registeredStudents = new HashSet<>();


    public String getDateTime() {
        return dateTime;
    }

    public Consultation dateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Consultation(String dateTime, Boolean cancelled, User teacher, Set<String> registeredStudents) {
        this.dateTime = dateTime;
        this.cancelled = cancelled;
        this.teacher = teacher;
        this.registeredStudents = registeredStudents;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean isCancelled() {
        return cancelled;
    }

    public Consultation cancelled(Boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public User getTeacher() {
        return teacher;
    }

    public Consultation teacher(User user) {
        this.teacher = user;
        return this;
    }

    public void setTeacher(User user) {
        this.teacher = user;
    }

    public Set<String> getRegisteredStudents() {
        return registeredStudents;
    }

    public Consultation registeredStudents(Set<String> users) {
        this.registeredStudents = users;
        return this;
    }

    public Consultation addRegisteredStudents(String user) {
        registeredStudents.add(user);
        return this;
    }

    public Consultation removeRegisteredStudents(String user) {
        registeredStudents.remove(user);
        return this;
    }

    public void setRegisteredStudents(Set<String> users) {
        this.registeredStudents = users;
    }


    @Override
    public String toString() {
        return "Consultation{" +
                ", dateTime='" + dateTime + "'" +
                ", cancelled='" + cancelled + "'" +
                '}';
    }
}
