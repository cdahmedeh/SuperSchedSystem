package schedule;

public class CourseBlock {
	private String name = "ZZZ0000";
	private String type = "LEC1";
	private String section = "A";
	
	public CourseBlock(String name, String type, String section) {
		this.name = name;
		this.type = type;
		this.section = section;
	}

	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}
	
	@Override
	public String toString() {
		return name + ' ' + type + ' ' + section;
	}
}
