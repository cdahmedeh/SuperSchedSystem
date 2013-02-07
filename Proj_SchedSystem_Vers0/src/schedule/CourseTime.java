package schedule;

import org.joda.time.LocalTime;

public class CourseTime {
	private LocalTime begin;
	private LocalTime end;
	private DayOfWeek day;
	
	public CourseTime(DayOfWeek day, int hour, int minute, int duration) {
		this.day = day;
		
		this.begin = new LocalTime(hour, minute);
		this.end = begin.plusMinutes(duration);
	}
	
	public CourseTime(DayOfWeek day, int startHour, int startMinute, int endHour, int endMinute) {
		this.day = day;
		
		this.begin = new LocalTime(startHour, startMinute);
		this.end = new LocalTime(endHour, endMinute);
	}
	
	public LocalTime getCourseBegins(){
		return this.begin;
	}
	
	public LocalTime getCourseEnds(){
		return this.end;
	}
	
	@Override
	public String toString() {
		return day.getName() + " " + begin.toString("HH:mm") + "-" + end.toString("HH:mm");
	}
}
