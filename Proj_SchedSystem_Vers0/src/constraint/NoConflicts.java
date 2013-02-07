package constraint;

import schedule.Schedule;

public class NoConflicts implements Constraint {

	@Override
	public boolean verify(Schedule schedule) {
		return getScore(schedule) == 0;
	}

	@Override
	public int getScore(Schedule schedule) {
		return schedule.findConflicts().size() * 100;
	}

}
