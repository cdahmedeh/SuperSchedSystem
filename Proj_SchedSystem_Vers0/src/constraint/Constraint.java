package constraint;

import schedule.Schedule;

public interface Constraint {
	public boolean verify(Schedule schedule);
	public int getScore(Schedule schedule);
}
