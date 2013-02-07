package student;

import java.util.ArrayList;

import schedule.Schedule;

import constraint.Constraint;

public class Student {
	private ArrayList<Constraint> constraints = new ArrayList<>();
	private Schedule schedule;
	
	
	public void addConstraint(Constraint constraint){
		constraints.add(constraint);
	}
	
	public int getTotalRestraintsScore(){
		int score = 0;
		
		for (Constraint constraint: constraints){
			score += constraint.getScore(schedule);
		}
		
		return score;
	}
	
	public String explainConstraints(){
		StringBuilder sb = new StringBuilder();
		
		for (Constraint constraint: constraints){
			sb.append(constraint.getClass().getName());
			sb.append(" = ");
			sb.append(constraint.getScore(schedule));
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
}
