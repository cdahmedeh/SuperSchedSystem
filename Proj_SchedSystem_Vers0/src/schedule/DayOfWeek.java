package schedule;

public enum DayOfWeek {
	MONDAY("Monday"),
	TUESDAY("Tuesday"),
	WEDNESDAY("Wednesday"),
	THURSDAY("Thursday"),
	FRIDAY("Friday");

	private String name;
	public String getName() {return this.name;}
	
	private DayOfWeek(String name) {
		this.name = name;
	}
}
