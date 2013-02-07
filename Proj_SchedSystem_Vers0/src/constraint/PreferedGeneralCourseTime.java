package constraint;

import org.joda.time.LocalTime;

import schedule.CourseBlock;
import schedule.Schedule;

public class PreferedGeneralCourseTime implements Constraint {
	private LocalTime prefBegin;
	private LocalTime prefEnd;
	
	public PreferedGeneralCourseTime(int prefHourBegin, int prefMinuteBegin, int prefHourEnd, int prefMinuteEnd) {
		this.prefBegin = new LocalTime(prefHourBegin, prefMinuteBegin);
		this.prefEnd = new LocalTime(prefHourEnd, prefMinuteEnd);
	}
	
	@Override
	public boolean verify(Schedule schedule) {
		return getScore(schedule) == 0;
	}

	@Override
	public int getScore(Schedule schedule) {
		int numOfProblematicCourses = 0;
		
		for (CourseBlock block: schedule.getAllCourseBlocks()){
			if (
					block.getCourseTime().getCourseBegins().isAfter(prefEnd) || 
					block.getCourseTime().getCourseEnds().isAfter(prefEnd) ||
					block.getCourseTime().getCourseBegins().isBefore(prefBegin) ||
					block.getCourseTime().getCourseEnds().isBefore(prefBegin)
			){
				numOfProblematicCourses++;
			}
		}
		
		return numOfProblematicCourses;
	}
	
}
