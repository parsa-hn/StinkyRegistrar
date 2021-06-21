package domain;

import domain.exceptions.EnrollmentRulesViolationException;

import java.util.List;

public class EnrollController {

    public void enroll(Student student, List<OfferedCourse> courses) throws EnrollmentRulesViolationException {
        checkCoursesNotPassed(student, courses);
        checkPrerequisitesPassed(student, courses);
        checkCoursesExamTimesDontConflict(courses);
        checkCoursesNotTakenTwice(courses);
        checkSemesterUnitsLimit(student, courses);

        for (OfferedCourse offeredCourse : courses)
            student.takeCourse(offeredCourse.getCourse(), offeredCourse.getSection());
    }

    private void checkCoursesNotPassed(Student student, List<OfferedCourse> courses) throws EnrollmentRulesViolationException {
        for (OfferedCourse offeredCourse : courses) {
            if (student.hasPassedCourse(offeredCourse.getCourse()))
                throw new EnrollmentRulesViolationException(
                        String.format("The student has already passed %s", offeredCourse.getCourse().getName())
                );
        }
    }

    private void checkPrerequisitesPassed(Student student, List<OfferedCourse> courses) throws EnrollmentRulesViolationException {
        for (OfferedCourse offeredCourse : courses) {
            List<Course> prerequisites = offeredCourse.getCourse().getPrerequisites();
            for (Course prerequisite : prerequisites) {
                if (!student.hasPassedCourse(prerequisite))
                    throw new EnrollmentRulesViolationException(
                            String.format("The student has not passed %s as a prerequisite of %s",
                                    prerequisite.getName(), offeredCourse.getCourse().getName())
                    );
            }
        }
    }

    private void checkCoursesExamTimesDontConflict(List<OfferedCourse> courses) throws EnrollmentRulesViolationException {
        for (OfferedCourse course1 : courses) {
            for (OfferedCourse course2 : courses) {
                if (course1 == course2)
                    continue;
                if (course1.getExamDate().equals(course2.getExamDate()))
                    throw new EnrollmentRulesViolationException(
                            String.format("Two offerings %s and %s have the same exam time", course1, course2)
                    );
            }
        }
    }

    private void checkCoursesNotTakenTwice(List<OfferedCourse> courses) throws EnrollmentRulesViolationException {
        for (OfferedCourse course1 : courses) {
            for (OfferedCourse course2 : courses) {
                if (course1 == course2)
                    continue;
                if (course1.getCourse().equals(course2.getCourse()))
                    throw new EnrollmentRulesViolationException(
                            String.format("%s is requested to be taken twice", course1.getCourse().getName())
                    );
            }
        }
    }

    private void checkSemesterUnitsLimit(Student student, List<OfferedCourse> courses) throws EnrollmentRulesViolationException {
        double gpa = student.calculateGpa();
        int unitsRequested = 0;
        for (OfferedCourse course : courses) unitsRequested += course.getCourse().getUnits();

        if ((gpa < Globals.C_GRADE_STUDENTS_MINIMUM_GPA && unitsRequested > Globals.C_GRADE_STUDENTS_MAXIMUM_UNITS_PER_SEMESTER)
                || (gpa < Globals.B_GRADE_STUDENTS_MINIMUM_GPA && unitsRequested > Globals.B_GRADE_STUDENTS_MAXIMUM_UNITS_PER_SEMESTER)
                || (unitsRequested > Globals.MAXIMUM_UNITS_PER_SEMESTER))
            throw new EnrollmentRulesViolationException(
                    String.format("Number of units (%d) requested does not match GPA of %f", unitsRequested, gpa)
            );
    }
}
