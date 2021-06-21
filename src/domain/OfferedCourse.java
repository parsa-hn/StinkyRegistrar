package domain;

import java.util.Date;

public class OfferedCourse {
    private final Course course;
    private final int section;
    private final Date examDate;

    public static int DEFAULT_SECTION = 1;

    public OfferedCourse(Course course, Date examDate) {
        this.course = course;
        this.section = DEFAULT_SECTION;
        this.examDate = examDate;
    }

    public OfferedCourse(Course course, int section) {
        this.course = course;
        this.section = section;
        this.examDate = null;
    }

    public Course getCourse() {
        return course;
    }

    public Date getExamDate() {
        return examDate;
    }

    public int getSection() {
        return section;
    }

    @Override
    public String toString() {
        return course.getName() + " - " + section;
    }
}
