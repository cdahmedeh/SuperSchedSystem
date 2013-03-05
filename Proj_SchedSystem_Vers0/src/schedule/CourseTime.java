package schedule;

import time.SuperFastTime;

public class CourseTime {
	private SuperFastTime begin;
	private SuperFastTime end;
	private DayOfWeek day;
	
	public CourseTime(DayOfWeek day, int hour, int minute, int duration) {
		this.day = day;
		
		this.begin = new SuperFastTime(hour, minute);
		this.end = begin.plusMinutes(duration);
	}
	
	public CourseTime(DayOfWeek day, int startHour, int startMinute, int endHour, int endMinute) {
		this.day = day;
		
		this.begin = new SuperFastTime(startHour, startMinute);
		this.end = new SuperFastTime(endHour, endMinute);
	}
	
	public SuperFastTime getBegin(){
		return this.begin;
	}
	
	public SuperFastTime getEnd(){
		return this.end;
	}
	
	public DayOfWeek getDay(){
		return this.day;
	}
	
		
	public SuperFastTime getCourseBegins(){
		return this.begin;
	}
	
	public SuperFastTime getCourseEnds(){
		return this.end;
	}
	
	@Override
	public String toString() {
		return day.getName() + " " + begin.toString("HH:mm") + "-" + end.toString("HH:mm");
	}
}
