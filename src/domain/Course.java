package domain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Course {
	private String id;
	private String name;
	private int units;
	private List<Course> prerequisites;

	public Course(String id, String name, int units) {
		this.id = id;
		this.name = name;
		this.units = units;
		prerequisites = new ArrayList<Course>();
	}

	public void addPrerequisites(Course... pres) {
		prerequisites.addAll(Arrays.asList(pres));
	}

	public List<Course> getPrerequisites() {
		return prerequisites;
	}

	public String getName() {
		return name;
	}

	public int getUnits() { return units; }

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" {");
		for (Course pre : prerequisites) {
			sb.append(pre.getName());
			sb.append(", ");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Course))
			return false;
		return id.equals(((Course)obj).getId());
	}
}
