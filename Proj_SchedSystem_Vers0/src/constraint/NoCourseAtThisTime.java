package constraint;

import org.joda.time.LocalTime;

import schedule.CourseBlock;
import schedule.DayOfWeek;
import schedule.Schedule;

public class NoCourseAtThisTime implements Constraint {
	private LocalTime prefBegin;
	private LocalTime prefEnd;
	private DayOfWeek dayOfWeek;
	
	public NoCourseAtThisTime(int prefHourBegin, int prefMinuteBegin, int prefHourEnd, int prefMinuteEnd, DayOfWeek dayOfWeek) {
		this.prefBegin = new LocalTime(prefHourBegin, prefMinuteBegin);
		this.prefEnd = new LocalTime(prefHourEnd, prefMinuteEnd);
		this.dayOfWeek = dayOfWeek;
	}
	
	@Override
	public boolean verify(Schedule schedule) {
		return getScore(schedule) == 0;
	}

	@Override
	public int getScore(Schedule schedule) {
		int numOfProblematicCourses = 0;
		
		for (CourseBlock block: schedule.getAllCourseBlocks()){
			if (block.getCourseTime().getDay().equals(dayOfWeek) && (
					(block.getCourseTime().getCourseBegins().isAfter(prefBegin) && block.getCourseTime().getCourseBegins().isBefore(prefEnd)) ||
					(block.getCourseTime().getCourseEnds().isAfter(prefBegin) && block.getCourseTime().getCourseEnds().isBefore(prefEnd))
				)
			) {
				numOfProblematicCourses++;
			}
		}
		
		return numOfProblematicCourses*10;
	}
	
	@Override
	public Constraint clone() {
//		NoConflicts cloned = new NoConflicts();
//		return cloned;
		return null;
	}

}
