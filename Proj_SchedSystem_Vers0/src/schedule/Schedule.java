package schedule;

import java.util.ArrayList;
import java.util.HashMap;

public class Schedule {
	private HashMap<CourseBlock, CourseTime> schedule = new HashMap<>();
	
	public void addCourseBlock(CourseBlock block, CourseTime time) {schedule.put(block, time);}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (CourseBlock block: schedule.keySet()){
			sb.append(block);
			sb.append(" ");
			sb.append(schedule.get(block));
			sb.append("\n");
		}
		
		return sb.toString();
	}

	public ArrayList<Conflict> findConflicts() {
		//TODO: Replace stub.
		
		ArrayList<Conflict> conflicts = new ArrayList<>();
		
		conflicts.add(
				new Conflict(
						new CourseBlock("SEG0000", "LEC1", "A"),
						new CourseBlock("SEG0001", "FUN1", "B"),
						new CourseTime(DayOfWeek.MONDAY, 12, 30, 1, 30)
				)
		);
		
		return conflicts;
	}
}
