package runs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import constraint.MustHaveCourse;
import constraint.NoConflicts;
import constraint.PreferedGeneralCourseTime;

import schedule.CourseBlock;
import schedule.CourseTime;
import schedule.DayOfWeek;
import student.Student;

public class Run04 {
	public static void main(String[] args) {
		//Create some students who want some courses.
		ArrayList<Student> students = new ArrayList<>();
		
		Student student1 = new Student();
		student1.addConstraint(new MustHaveCourse("MAT1201"));
		student1.addConstraint(new MustHaveCourse("MAT1221"));
		student1.addConstraint(new MustHaveCourse("PHY1725"));
		student1.addConstraint(new MustHaveCourse("ENG5201"));
		student1.addConstraint(new MustHaveCourse("ELG2790"));
		student1.addConstraint(new PreferedGeneralCourseTime(8, 30, 15, 30));
		student1.addConstraint(new NoConflicts());
		students.add(student1);
		
		Student student2 = new Student();
		student2.addConstraint(new MustHaveCourse("MAT1201"));
		student2.addConstraint(new MustHaveCourse("MAT1221"));
		student2.addConstraint(new MustHaveCourse("PHY1725"));
		student2.addConstraint(new MustHaveCourse("ENG5201"));
		student2.addConstraint(new MustHaveCourse("ELG2790"));
		student2.addConstraint(new PreferedGeneralCourseTime(8, 30, 15, 30));
		student2.addConstraint(new NoConflicts());
		students.add(student2);
		
		Student student3 = new Student();
		student3.addConstraint(new MustHaveCourse("MAT1201"));
		student3.addConstraint(new MustHaveCourse("MAT1221"));
		student3.addConstraint(new MustHaveCourse("CSI2910"));
		student3.addConstraint(new MustHaveCourse("CSI2100"));
		student3.addConstraint(new MustHaveCourse("SEG2919"));
		student3.addConstraint(new PreferedGeneralCourseTime(12, 30, 20, 30));
		student3.addConstraint(new NoConflicts());
		students.add(student3);
		
		Student student4 = new Student();
		student4.addConstraint(new MustHaveCourse("MAT1221"));
		student4.addConstraint(new MustHaveCourse("PHY1725"));
		student4.addConstraint(new MustHaveCourse("ENG5201"));
		student4.addConstraint(new MustHaveCourse("MAT1201"));
		student4.addConstraint(new MustHaveCourse("ENG5201"));
		student4.addConstraint(new PreferedGeneralCourseTime(11, 30, 22, 30));
		student4.addConstraint(new NoConflicts());
		students.add(student4);
		
		//Check all the courses that students want
		HashMap<String, Integer> coursesWanted = new HashMap<>();
		
		for (Student student: students){
			for (String course: student.getWantedCourses()){
				coursesWanted.put(course, 0);
			}
		}
		
		System.out.println(coursesWanted.keySet());
		
		//Create all these courses. Don't set the times yet
		ArrayList<CourseBlock> courseBlocksAll = new ArrayList<>();
		
		for (String course: coursesWanted.keySet()){
			CourseBlock lec1 = new CourseBlock(course, "LEC1", "A", null);
			CourseBlock lec2 = new CourseBlock(course, "LEC2", "A", null);
			CourseBlock tut1 = new CourseBlock(course, "TUT1", "A", null);
			CourseBlock lab1 = new CourseBlock(course, "LAB1", "A", null);
			
			courseBlocksAll.add(lec1);
			courseBlocksAll.add(lec2);
			courseBlocksAll.add(tut1);
			courseBlocksAll.add(lab1);		
		}
		
		System.out.println(courseBlocksAll);
		
		//Give students all courses that they need.
		for (Student student: students){
			for (String course: student.getWantedCourses()){
				for (CourseBlock block: courseBlocksAll) {
					if (block.getCourseName().equals(course)){
						student.getSchedule().addCourseBlock(block);
					}
				}
			}
		}
		
		//Print all their schedules.
		for (Student student: students){
			System.out.println(student.toString());
			System.out.println(student.getSchedule().toString());
		}
		
		//Create time slots for every course.
		for (CourseBlock block: courseBlocksAll){
			int dow = new Random().nextInt(5);
			DayOfWeek dowr = DayOfWeek.MONDAY ;
			switch(dow){
			case 0: dowr = DayOfWeek.MONDAY; break;
			case 1: dowr = DayOfWeek.TUESDAY; break;
			case 2: dowr = DayOfWeek.WEDNESDAY; break;
			case 3: dowr = DayOfWeek.THURSDAY; break;
			case 4: dowr = DayOfWeek.FRIDAY; break;
			}
						
			int hour = new Random().nextInt(24);
			int minute = new Random().nextInt(60);
			int defaultDuration = 90;
			block.setCourseTime(new CourseTime(dowr, hour, minute, defaultDuration));
		}
		
		//Print all their schedules.
		for (Student student: students){
			System.out.println(student.toString());
			System.out.println(student.getSchedule().toString());
			System.out.println(student.getTotalRestraintsScore());
			System.out.println(student.explainConstraints());
		}
	}
}
