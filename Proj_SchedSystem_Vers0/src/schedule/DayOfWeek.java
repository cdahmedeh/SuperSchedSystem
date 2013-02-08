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
	
	public static DayOfWeek getDayOnNumber(int i){
		switch(i){
		case(0):
			return MONDAY;
		case(1):
			return TUESDAY;
		case(2):
			return WEDNESDAY;
		case(3):
			return THURSDAY;
		case(4):
			return FRIDAY;
		}
		
		return MONDAY;
	}
}
