package runs;

import java.util.ArrayList;

import schedule.Conflict;
import schedule.CourseBlock;
import schedule.CourseTime;
import schedule.DayOfWeek;
import schedule.Schedule;

public class Run01 {
	public static void main(String[] args) {
		Schedule sched = new Schedule();
		
		sched.addCourseBlock(new CourseBlock("CSI3010", "LEC1", "A", new CourseTime(DayOfWeek.MONDAY, 11, 30, 90)));
		sched.addCourseBlock(new CourseBlock("CSI3010", "LEC2", "A", new CourseTime(DayOfWeek.MONDAY, 13, 0, 90)));
		sched.addCourseBlock(new CourseBlock("CSI3010", "LAB1", "A", new CourseTime(DayOfWeek.WEDNESDAY, 14, 30, 180)));
		sched.addCourseBlock(new CourseBlock("SEG9999", "TUT", "A", new CourseTime(DayOfWeek.WEDNESDAY, 9, 30, 180)));

		System.out.println(sched);
		
		System.out.println("START PRINT THE CONFLICTS!");
		for (Conflict conflict: sched.findConflicts()) System.out.println(conflict);
	}
}
