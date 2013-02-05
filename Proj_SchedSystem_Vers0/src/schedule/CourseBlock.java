package schedule;

public class CourseBlock {
	private String name = "ZZZ0000";
	private String type = "LEC1";
	
	public CourseBlock(String name, String type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}
	
	@Override
	public String toString() {
		return name + ' ' + type;
	}
}
