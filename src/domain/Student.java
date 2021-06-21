package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    private final String id;
    private final String name;
    private final Map<Term, Map<Course, Double>> transcript;
    private final List<OfferedCourse> currentSemesterCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.transcript = new HashMap<>();
        this.currentSemesterCourses = new ArrayList<>();
    }

    public void takeCourse(Course course, int section) {
        currentSemesterCourses.add(new OfferedCourse(course, section));
    }

    public void addTranscriptRecord(Course course, Term term, double grade) {
        if (!transcript.containsKey(term))
            transcript.put(term, new HashMap<>());
        transcript.get(term).put(course, grade);
    }

    public Map<Term, Map<Course, Double>> getTranscript() {
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
