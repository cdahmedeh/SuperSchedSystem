package schedule;

import java.util.ArrayList;

import org.joda.time.LocalTime;

public class Schedule {
	private ArrayList<CourseBlock> schedule = new ArrayList<>();
	
	public void addCourseBlock(CourseBlock block) {schedule.add(block);}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (CourseBlock block: schedule){
			sb.append(block);
			sb.append(" ");
			sb.append(block.getCourseTime());
			sb.append("\n");
		}
		
		return sb.toString();
	}

	public ArrayList<Conflict> findConflicts() {
		ArrayList<Conflict> conflicts = new ArrayList<>();
		LocalTime iStart;
		LocalTime iEnd;
		LocalTime jStart;
		LocalTime jEnd;
		DayOfWeek iDay;
		DayOfWeek jDay;

		for (int i = 0; i<schedule.size();i++){
			iStart = schedule.get(i).getCourseTime().getBegin();
			iEnd = schedule.get(i).getCourseTime().getEnd();
			iDay = schedule.get(i).getCourseTime().getDay();
			System.out.println("Checking day "+i);
			for (int j = i+1; j<schedule.size();j++){
				jStart = schedule.get(j).getCourseTime().getBegin();
				jEnd = schedule.get(j).getCourseTime().getEnd();
				jDay = schedule.get(j).getCourseTime().getDay();
				
				if (
						iDay.equals(jDay) &&
						(iStart.isEqual(jStart) ||
						iStart.isBefore(jStart) && iEnd.isAfter(jStart) ||
						jStart.isBefore(iStart) && jEnd.isAfter(iStart))
						){
					conflicts.add(
						new Conflict(				
							schedule.get(i),
							schedule.get(j),
							schedule.get(i).getCourseTime() //TODO: justify this vaule please... notice that there are three types of conflicts. Course times do not have to be identical
							)
					);
				}
			}
		}
		
		return conflicts;
	}
}
