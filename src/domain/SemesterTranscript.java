package domain;

import java.util.ArrayList;
import java.util.List;

public class SemesterTranscript {
    private Semester semester;
    private final List<TakenCourse> takenCourses;

    SemesterTranscript() {
        this.takenCourses = new ArrayList<>();
    }

    public void addRecord(Course course, Double grade) {
        this.takenCourses.add(new TakenCourse(course, grade));
    }

    public Semester getSemester() {
        return semester;
    }

    public List<TakenCourse> getTakenCourses() {
        return takenCourses;
    }
}
