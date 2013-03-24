package schedule;

public enum DayOfWeek {
	MONDAY("Monday", 0),
	TUESDAY("Tuesday", 1),
	WEDNESDAY("Wednesday", 2),
	THURSDAY("Thursday", 3),
	FRIDAY("Friday", 4);

	private String name;
	public String getName() {return this.name;}
	
	private int value = -1;
	public int getValue() {return value;}
	
	private DayOfWeek(String name, int value) {
		this.name = name;
		this.value = value;
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
