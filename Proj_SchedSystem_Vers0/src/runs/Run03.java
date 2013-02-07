package runs;

import constraint.MustHaveCourse;
import constraint.NoConflicts;
import constraint.PreferedGeneralCourseTime;
import schedule.CourseBlock;
import schedule.CourseTime;
import schedule.DayOfWeek;
import schedule.Schedule;
import student.Student;

public class Run03 {
	public static void main(String[] args) {
		//Create some courses
		CourseBlock SEG1010A_LEC1 = new CourseBlock("SEG1010", "LEC1", "A", new CourseTime(DayOfWeek.MONDAY, 8, 30, 90));
		CourseBlock SEG1010A_LEC2 = new CourseBlock("SEG1010", "LEC2", "A", new CourseTime(DayOfWeek.WEDNESDAY, 12, 30, 90));
		CourseBlock SEG1010A_LAB1 = new CourseBlock("SEG1010", "LAB1", "A", new CourseTime(DayOfWeek.FRIDAY, 16, 30, 180));
		CourseBlock SEG1010A_TUT1 = new CourseBlock("SEG1010", "TUT1", "A", new CourseTime(DayOfWeek.FRIDAY, 22, 30, 90));
		
		CourseBlock FRA9999A_LEC1 = new CourseBlock("FRA9999A", "LEC1", "A", new CourseTime(DayOfWeek.TUESDAY, 10, 30, 300));
				
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
		
		//Create students and give them the scheduled
		Student student1 = new Student();
		student1.setSchedule(sched1);
		
		Student student2 = new Student();
		student2.setSchedule(sched2);
		
		//Give the students some constraints
		student1.addConstraint(new NoConflicts());
		student1.addConstraint(new MustHaveCourse("SEG1010"));
		student1.addConstraint(new MustHaveCourse("FRA9999"));
		student1.addConstraint(new PreferedGeneralCourseTime(8, 30, 15, 30));
		
		student2.addConstraint(new NoConflicts());
		student2.addConstraint(new MustHaveCourse("MAT3921"));
		student2.addConstraint(new PreferedGeneralCourseTime(15, 30, 19, 30));
		
		//Check the constraints
		System.out.println(student1.getTotalRestraintsScore());
		System.out.println(student1.explainConstraints());
		
		System.out.println(student2.getTotalRestraintsScore());
		System.out.println(student2.explainConstraints());
	}
}
