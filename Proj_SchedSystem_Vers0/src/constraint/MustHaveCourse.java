package constraint;

import schedule.CourseBlock;
import schedule.Schedule;

public class MustHaveCourse implements Constraint {

	private String mustHaveCourseName = "";
	
	public MustHaveCourse(String courseName) {
		this.mustHaveCourseName = courseName;
	}
	
	@Override
	public boolean verify(Schedule schedule) {
		return getScore(schedule) == 0;
	}

	@Override
	public int getScore(Schedule schedule) {
		for (CourseBlock block: schedule.getAllCourseBlocks()){
			if (block.getCourseName().equals(mustHaveCourseName)){
				return 0;
			}
			
		}
		
		return 10;
	}

}
