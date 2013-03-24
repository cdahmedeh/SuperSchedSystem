package constraint;
import schedule.Schedule;
public interface Constraint extends Cloneable {
	public boolean verify(Schedule schedule);
	public int getScore(Schedule schedule);
	public Constraint clone();
}
