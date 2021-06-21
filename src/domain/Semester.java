package domain;

import java.sql.Date;

public class Semester {
    private final String name;
    private final Date startDate;

    public Semester(String name) {
        this.name = name;
        this.startDate = null;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return name;
    }
}
