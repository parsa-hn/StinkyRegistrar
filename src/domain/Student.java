package domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String id;
    private final String name;
    private final Transcript transcript = new Transcript();
    private final List<OfferedCourse> currentSemesterCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.currentSemesterCourses = new ArrayList<>();
    }

    public void takeCourse(Course course, int section) {
        currentSemesterCourses.add(new OfferedCourse(course, section));
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public List<OfferedCourse> getCurrentSemesterCourses() {
        return currentSemesterCourses;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<TakenCourse> getPassedCourses() {
        List<TakenCourse> takenCourses = new ArrayList<>();
        List<SemesterTranscript> semestersTranscripts = transcript.getSemesterTranscripts();
        for (SemesterTranscript semesterTranscript : semestersTranscripts)
            takenCourses.addAll(semesterTranscript.getTakenCourses());

        return takenCourses;
    }

    public boolean hasPassedCourse(Course course) {
        List<TakenCourse> takenCourses = getPassedCourses();
        for (TakenCourse takenCourse : takenCourses) {
            if (takenCourse.getCourse().equals(course) && takenCourse.getGrade() >= Globals.MINIMUM_GRADE_TO_PASS)
                return true;
        }
        return false;
    }

    public Double calculateGpa() {
        double totalGradeSum = 0d;
        int totalUnits = 0;
        for (SemesterTranscript semesterTranscript : transcript.getSemesterTranscripts()) {
            for (TakenCourse takenCourse : semesterTranscript.getTakenCourses()) {
                totalGradeSum += takenCourse.getGrade() * takenCourse.getCourse().getUnits();
                totalUnits += takenCourse.getCourse().getUnits();
            }
        }
        return totalGradeSum / totalUnits;
    }

    public int getCurrentSemesterTotalUnits() {
        int totalUnits = 0;
        for (OfferedCourse offeredCourse : currentSemesterCourses) {
            totalUnits += offeredCourse.getCourse().getUnits();
        }
        return totalUnits;
    }
}
