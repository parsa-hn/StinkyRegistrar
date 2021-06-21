package domain;
import java.util.Date;

public class OfferedCourse {
	private final Course course;
	private final int section;
	private final Date examDate;

	public static int DEFAULT_SECTION = 1;

	public OfferedCourse(Course course) {
		this.course = course;
		this.section = DEFAULT_SECTION;
		this.examDate = null;
	}

	public OfferedCourse(Course course, Date examDate) {
		this.course = course;
		this.section = DEFAULT_SECTION;
		this.examDate = examDate;
	}

	public OfferedCourse(Course course, Date examDate, int section) {
		this.course = course;
		this.section = section;
		this.examDate = examDate;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public String toString() {
		return course.getName() + " - " + section;
	}
	
	public Date getExamTime() {
		return examDate;
	}

	public int getSection() { return section; }
}
