package student;

import java.util.ArrayList;
import java.util.HashMap;

import schedule.CourseBlock;
import schedule.Schedule;

import constraint.Constraint;
import constraint.MustHaveCourse;

public class Student implements Cloneable{
	private ArrayList<Constraint> constraints = new ArrayList<>();
	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}
	private Schedule schedule = new Schedule();
	private ArrayList<String> listOfCourses;
	
	
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
		if (listOfCourses == null) {
			listOfCourses = new ArrayList<>() ;

			for (Constraint constraint: constraints){
				if (constraint instanceof MustHaveCourse){
					getListOfCourses().add(((MustHaveCourse)constraint).getCourseName());
				}
			}
		}
		return listOfCourses;
	}
	
	public ArrayList<String> getWantedCoursesNonOptimzed(){
			listOfCourses = new ArrayList<>() ;

			for (Constraint constraint: constraints){
				if (constraint instanceof MustHaveCourse){
					getListOfCourses().add(((MustHaveCourse)constraint).getCourseName());
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

	public void register(HashMap<String, CourseBlock> blocks) {
		schedule = new Schedule();
		
		for (String course: this.getWantedCourses()){
			if (blocks.get(course) != null) {
				schedule.addCourseBlock(blocks.get(course));
			}
		}
	}
	
	/**
	 * (Nic) Note: This clone function ommits the schedule of the students
	 */
	public Student clone(){
		Student cloned = new Student();
		for(Constraint constraint: constraints){
			cloned.addConstraint(constraint.clone());
		}
		cloned.setListOfCourses(getListOfCourses());		
		return cloned;
	}

	public ArrayList<String> getListOfCourses() {
		return listOfCourses;
	}

	public void setListOfCourses(ArrayList<String> listOfCourses) {
		this.listOfCourses = listOfCourses;
	}
	
	private String username = "";
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Student() {
	}
	
	public Student(String username) {
		this.username = username;
	}
}
