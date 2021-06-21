package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transcript {
    private final Map<Semester, SemesterTranscript> semesterTranscripts = new HashMap<>();

    Transcript() {
    }

    public void addRecord(Course course, Semester semester, double grade) {
        this.semesterTranscripts.putIfAbsent(semester, new SemesterTranscript());
        this.semesterTranscripts.get(semester).addRecord(course, grade);
    }

    public List<SemesterTranscript> getSemesterTranscripts() {
        return new ArrayList<>(this.semesterTranscripts.values());
    }
}