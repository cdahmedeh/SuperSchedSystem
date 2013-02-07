package schedule;

import java.util.ArrayList;
import java.util.HashMap;

public class Schedule {
	private ArrayList<CourseBlock> schedule = new ArrayList<>();
	
	public void addCourseBlock(CourseBlock block) {schedule.add(block);}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (CourseBlock block: schedule){
			sb.append(block);
			sb.append(" ");
			sb.append(block.getCourseTime());
			sb.append("\n");
		}
		
		return sb.toString();
	}

	public ArrayList<Conflict> findConflicts() {
		//TODO: Replace stub.
		
		ArrayList<Conflict> conflicts = new ArrayList<>();
		
		conflicts.add(
				new Conflict(
						new CourseBlock("SEG0000", "LEC1", "A", new CourseTime(DayOfWeek.MONDAY, 12, 00, 120)),
						new CourseBlock("SEG0001", "FUN1", "B", new CourseTime(DayOfWeek.MONDAY, 12, 00, 120)),
						new CourseTime(DayOfWeek.MONDAY, 12, 30, 1, 30)
				)
		);
		
		return conflicts;
	}
}
