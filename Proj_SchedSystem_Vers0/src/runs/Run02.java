package runs;

import schedule.CourseBlock;
import schedule.CourseTime;
import schedule.DayOfWeek;
import schedule.Schedule;

public class Run02 {
	public static void main(String[] args) {
		//Create some courses
		CourseBlock SEG1010A_LEC1 = new CourseBlock("SEG1010", "LEC1", "A", new CourseTime(DayOfWeek.MONDAY, 8, 30, 90));
		CourseBlock SEG1010A_LEC2 = new CourseBlock("SEG1010", "LEC2", "A", new CourseTime(DayOfWeek.WEDNESDAY, 12, 30, 90));
		CourseBlock SEG1010A_LAB1 = new CourseBlock("SEG1010", "LAB1", "A", new CourseTime(DayOfWeek.FRIDAY, 16, 30, 180));
		CourseBlock SEG1010A_TUT1 = new CourseBlock("SEG1010", "TUT1", "A", new CourseTime(DayOfWeek.FRIDAY, 22, 30, 90));
		
		CourseBlock FRA9999A_LEC1 = new CourseBlock("FRA9999", "LEC1", "A", new CourseTime(DayOfWeek.TUESDAY, 10, 30, 300));
				
		CourseBlock MAT3921Z_LEC1 = new CourseBlock("MAT3921", "LEC1", "Z", new CourseTime(DayOfWeek.WEDNESDAY, 17, 30, 90));
		CourseBlock MAT3921Z_LEC2 = new CourseBlock("MAT3921", "LEC2", "Z", new CourseTime(DayOfWeek.MONDAY, 12, 30, 90));
		CourseBlock MAT3921Z_LAB1 = new CourseBlock("MAT3921", "LAB1", "Z", new CourseTime(DayOfWeek.FRIDAY, 18, 30, 180));
		
		//Schedules for some students
		Schedule sched1 = new Schedule();
		Schedule sched2 = new Schedule();
		
		//Get some students into some courses
		sched1.addCourseBlock(MAT3921Z_LEC1);
		sched1.addCourseBlock(MAT3921Z_LEC2);
		sched1.addCourseBlock(MAT3921Z_LAB1);
		sched1.addCourseBlock(FRA9999A_LEC1);
		
		sched2.addCourseBlock(SEG1010A_TUT1);
		sched2.addCourseBlock(SEG1010A_LAB1);
		sched2.addCourseBlock(SEG1010A_LEC2);
		sched2.addCourseBlock(SEG1010A_LEC1);
		sched2.addCourseBlock(FRA9999A_LEC1);
		
		//Print their stuff
		System.out.println(sched1);
		System.out.println(sched2);
	}
}
