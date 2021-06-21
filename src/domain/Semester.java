package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Semester {
	private String name;
	private Date startDate;

	public Semester(String name) {
		this.name = name;
		this.startDate = null;
	}

	public Semester(String name, Date startDate) {
		this.name = name;
		this.startDate = startDate;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	
}
