package schedule;

public class CourseBlock {
	private String name = "ZZZ0000";
	private String type = "LEC1";
	private String section = "A";
	private CourseTime times;
	
	public CourseBlock(String name, String type, String section, CourseTime times) {
		this.name = name;
		this.type = type;
		this.section = section;
		this.times = times;
	}

	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}
	
	@Override
	public String toString() {
		return name + ' ' + type + ' ' + section;
	}

	public CourseTime getCourseTime() {
		return this.times;
	}

	public void setCourseTime(CourseTime times) {
		this.times = times;
	}
	
	public String getCourseName() {
		return this.name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
	
	@Override
	public int hashCode() {
		return times.getDay().getValue()*10000+times.getBegin().getHourOfDay()*100+times.getBegin().getMinuteOfHour();
	}
}
