package constraint;

import schedule.CourseBlock;
import schedule.Schedule;
import time.SuperFastTime;

public class PreferedGeneralCourseTime implements Constraint {
	private SuperFastTime prefBegin;
	private SuperFastTime prefEnd;
	
	public PreferedGeneralCourseTime(int prefHourBegin, int prefMinuteBegin, int prefHourEnd, int prefMinuteEnd) {
		this.prefBegin = new SuperFastTime(prefHourBegin, prefMinuteBegin);
		this.prefEnd = new SuperFastTime(prefHourEnd, prefMinuteEnd);
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
	
	@Override
	public Constraint clone() {
		PreferedGeneralCourseTime cloned = new PreferedGeneralCourseTime(prefBegin.getHourOfDay(),prefBegin.getMinuteOfHour(),prefEnd.getHourOfDay(),prefEnd.getMinuteOfHour());
		return cloned;
	}

	public SuperFastTime getPrefBegin() {
		return prefBegin;
	}

	public void setPrefBegin(SuperFastTime prefBegin) {
		this.prefBegin = prefBegin;
	}

	public SuperFastTime getPrefEnd() {
		return prefEnd;
	}

	public void setPrefEnd(SuperFastTime prefEnd) {
		this.prefEnd = prefEnd;
	}
}
