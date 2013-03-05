package time;

public class SuperFastTime {

	private int hour = 0;
	private int minute = 0;
	
	public SuperFastTime(int hour, int minutes) {
		this.hour = hour;
		this.minute = minutes;
	}

	public String toString(String string) {
		StringBuilder sb = new StringBuilder();
		sb.append(hour);
		sb.append(':');
		sb.append(minute);
		return sb.toString() ;
	}

	public SuperFastTime plusMinutes(int minutes) {
		int newhr = hour + (minutes)/60;
		int newmins = minute  + (minutes)%60;
		
		return new SuperFastTime(newhr, newmins);
	}

	public int getHourOfDay() {
		return hour;
	}

	public int getMinuteOfHour() {
		return minute;
	}

	public boolean isAfter(SuperFastTime prefEnd) {
		if (this.hour > prefEnd.getHourOfDay()){
			return true;
		} else if (this.hour == prefEnd.getHourOfDay()){
			return this.minute > prefEnd.getMinuteOfHour();
		} else {
			return false;
		}
	}
	
	public boolean isBefore(SuperFastTime prefEnd) {
		if (this.hour < prefEnd.getHourOfDay()){
			return true;
		} else if (this.hour == prefEnd.getHourOfDay()){
			return this.minute < prefEnd.getMinuteOfHour();
		} else {
			return false;
		}
	}

	public boolean isEqual(SuperFastTime jStart) {
		return this.hour == jStart.getHourOfDay() && this.minute == jStart.getMinuteOfHour();
	}
}
