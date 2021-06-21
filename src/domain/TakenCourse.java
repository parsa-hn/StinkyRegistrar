package domain;

public class TakenCourse {
    private final Course course;
    private final Double grade;

    public TakenCourse(Course course, Double grade) {
        this.course = course;
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public Double getGrade() {
        return grade;
    }
}
