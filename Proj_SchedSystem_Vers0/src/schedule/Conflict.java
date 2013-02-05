package schedule;

import java.util.ArrayList;

public class Conflict {
	CourseBlock firstCourse;
	CourseBlock secondCourse;
	CourseTime conflictingTime;
		
	public Conflict(CourseBlock first, CourseBlock second, CourseTime conflictTime) {
		this.firstCourse = first;
		this.secondCourse = second;
		this.conflictingTime = conflictTime;
	}
	
	@Override
	public String toString() {
		return firstCourse + " conflicts with " + secondCourse + " on " + conflictingTime; 
	}
}
