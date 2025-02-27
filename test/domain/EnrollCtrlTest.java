package domain;

import static org.junit.Assert.*;

import java.util.*;

import domain.exceptions.EnrollmentRulesViolationException;
import org.junit.Before;
import org.junit.Test;

public class EnrollCtrlTest {
	private Student bebe;
	private Course prog;
	private Course ap;
	private Course dm;
	private Course math1;
	private Course math2;
	private Course phys1;
	private Course phys2;
	private Course maaref;
	private Course farsi;
	private Course english;
	private Course akhlagh;
	private Course economy;
	private Course karafarini;

	@Before
	public void setup() {
		math1 = new Course("4", "MATH1", 3);
		phys1 = new Course("8", "PHYS1", 3);
		prog = new Course("7", "PROG", 4);
		math2 = new Course("6", "MATH2", 3);
		math2.addPrerequisites(math1);
		phys2 = new Course("9", "PHYS2", 3);
		phys2.addPrerequisites(math1, phys1);
		ap = new Course("2", "AP", 3);
		ap.addPrerequisites(prog);
		dm = new Course("3", "DM", 3);
		dm.addPrerequisites(prog);
		economy = new Course("1", "ECO", 3);
		maaref = new Course("5", "MAAREF", 2);
		farsi = new Course("12", "FA", 2);
		english = new Course("10", "EN", 2);
		akhlagh = new Course("11", "AKHLAGH", 2);
		karafarini = new Course("13", "KAR", 3);

		bebe = new Student("1", "Bebe");
	}

	private ArrayList<OfferedCourse> requestedOfferings(Course...courses) {
		Calendar cal = Calendar.getInstance();
		ArrayList<OfferedCourse> result = new ArrayList<>();
		for (Course course : courses) {
			cal.add(Calendar.DATE, 1);
			result.add(new OfferedCourse(course, cal.getTime()));
		}
		return result;
	}

	private boolean hasTaken(Student s, Course...courses) {
	    Set<Course> coursesTaken = new HashSet<>();
		for (OfferedCourse oc : s.getCurrentSemesterCourses())
				coursesTaken.add(oc.getCourse());
		for (Course course : courses) {
			if (!coursesTaken.contains(course))
				return false;
		}
		return true;
	}

	@Test
	public void canTakeBasicCoursesInFirstTerm() throws EnrollmentRulesViolationException {
		new EnrollController().enroll(bebe, requestedOfferings(math1, phys1, prog));
		assertTrue(hasTaken(bebe, math1, phys1, prog));
	}

	@Test
	public void canTakeNoOfferings() throws EnrollmentRulesViolationException {
		new EnrollController().enroll(bebe, new ArrayList<>());
		assertTrue(hasTaken(bebe));
	}

	@Test(expected = EnrollmentRulesViolationException.class)
	public void cannotTakeWithoutPreTaken() throws EnrollmentRulesViolationException {
		new EnrollController().enroll(bebe, requestedOfferings(math2, phys1, prog));
	}

	@Test(expected = EnrollmentRulesViolationException.class)
	public void cannotTakeWithoutPrePassed() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 18);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 12);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 8.4);
		new EnrollController().enroll(bebe, requestedOfferings(math2, ap));
	}

	@Test
	public void canTakeWithPreFinallyPassed() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 18);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 12);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 8.4);

		bebe.addTranscriptRecord(phys2, new Semester("t2"), 10);
		bebe.addTranscriptRecord(ap, new Semester("t2"), 16);
		bebe.addTranscriptRecord(math1, new Semester("t2"), 10.5);

		new EnrollController().enroll(bebe, requestedOfferings(math2, dm));
		assertTrue(hasTaken(bebe, math2, dm));
	}

	@Test(expected = EnrollmentRulesViolationException.class)
	public void cannotTakeAlreadyPassed1() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 18);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 12);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 8.4);

		bebe.addTranscriptRecord(phys2, new Semester("t2"), 10);
		bebe.addTranscriptRecord(ap, new Semester("t2"), 16);
		bebe.addTranscriptRecord(math1, new Semester("t2"), 10.5);

		new EnrollController().enroll(bebe, requestedOfferings(math1, dm));
	}

	@Test(expected = EnrollmentRulesViolationException.class)
	public void cannotTakeAlreadyPassed2() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 18);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 12);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 8.4);

		bebe.addTranscriptRecord(phys2, new Semester("t2"), 10);
		bebe.addTranscriptRecord(ap, new Semester("t2"), 16);
		bebe.addTranscriptRecord(math1, new Semester("t2"), 10.5);

		new EnrollController().enroll(bebe, requestedOfferings(phys1, dm));
	}

	@Test(expected = EnrollmentRulesViolationException.class)
	public void cannotTakeOfferingsWithSameExamTime() throws EnrollmentRulesViolationException {
		Calendar cal = Calendar.getInstance();
		new EnrollController().enroll(bebe,
				List.of(
					new OfferedCourse(phys1, cal.getTime()),
					new OfferedCourse(math1, cal.getTime()),
					new OfferedCourse(phys1, cal.getTime())
				));
	}

	@Test(expected = EnrollmentRulesViolationException.class)
	public void cannotTakeACourseTwice() throws EnrollmentRulesViolationException {
		new EnrollController().enroll(bebe, requestedOfferings(phys1, dm, phys1));
	}

	@Test
	public void canTake14WithGPA11() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 13);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 11);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 9);

		new EnrollController().enroll(bebe, requestedOfferings(dm, math1, farsi, akhlagh, english, maaref));
		assertTrue(hasTaken(bebe, dm, math1, farsi, akhlagh, english, maaref));
	}

	@Test(expected = EnrollmentRulesViolationException.class)
	public void cannotTake15WithGPA11() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 13);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 11);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 9);

		new EnrollController().enroll(bebe, requestedOfferings(dm, math1, farsi, akhlagh, english, ap));
		assertTrue(hasTaken(bebe, dm, math1, farsi, akhlagh, english, ap));
	}

	@Test
	public void canTake15WithGPA12() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 15);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 12);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 9);

		new EnrollController().enroll(bebe, requestedOfferings(dm, math1, farsi, akhlagh, english, maaref));
		assertTrue(hasTaken(bebe, dm, math1, farsi, akhlagh, english, maaref));
	}

	@Test
	public void canTake15WithGPA15() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 15);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 15);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 15);

		new EnrollController().enroll(bebe, requestedOfferings(dm, math2, farsi, akhlagh, english, maaref));
		assertTrue(hasTaken(bebe, dm, math2, farsi, akhlagh, english, maaref));
	}

	@Test(expected = EnrollmentRulesViolationException.class)
	public void cannotTake18WithGPA15() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 15);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 15);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 15);

		new EnrollController().enroll(bebe, requestedOfferings(ap, dm, math2, farsi, akhlagh, english, ap));
		assertTrue(hasTaken(bebe, ap, dm, math2, farsi, akhlagh, english, ap));
	}

	@Test
	public void canTake20WithGPA16() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 16);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 16);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 16);

		new EnrollController().enroll(bebe, requestedOfferings(
				ap, dm, math2, phys2, economy, karafarini, farsi));
		assertTrue(hasTaken(bebe, ap, dm, math2, phys2, economy, karafarini, farsi));
	}

	@Test(expected = EnrollmentRulesViolationException.class)
	public void cannotTake24() throws EnrollmentRulesViolationException {
		bebe.addTranscriptRecord(phys1, new Semester("t1"), 16);
		bebe.addTranscriptRecord(prog, new Semester("t1"), 16);
		bebe.addTranscriptRecord(math1, new Semester("t1"), 16);

		new EnrollController().enroll(bebe, requestedOfferings(
				ap, dm, math2, phys2, economy, karafarini, farsi, akhlagh, english));
		assertTrue(hasTaken(bebe, ap, dm, math2, phys2, economy, karafarini, farsi, akhlagh, english));
	}


}