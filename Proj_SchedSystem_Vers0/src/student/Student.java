package student;

import java.util.ArrayList;

import schedule.CourseBlock;
import schedule.Schedule;

import constraint.Constraint;
import constraint.MustHaveCourse;

public class Student {
	private ArrayList<Constraint> constraints = new ArrayList<>();
	private Schedule schedule = new Schedule();
	
	
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
	
	public ArrayList<String> getWantedCourses(){
		ArrayList<String> listOfCourses = new ArrayList<>();
		
		for (Constraint constraint: constraints){
			if (constraint instanceof MustHaveCourse){
				listOfCourses.add(((MustHaveCourse)constraint).getCourseName());
			}
		}
		
		return listOfCourses;
	}
	
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	public Schedule getSchedule() {
		return this.schedule;
	}

	public void register(ArrayList<CourseBlock> blocks) {
		schedule = new Schedule();
		
		for (CourseBlock block: blocks){
			for (String course: this.getWantedCourses()){
				if (block.getCourseName().equals(course)) {
					schedule.addCourseBlock(block);
				}
			}
		}
	}
}
