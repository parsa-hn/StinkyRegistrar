package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
