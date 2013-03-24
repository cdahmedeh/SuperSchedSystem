package schedule;

import java.util.ArrayList;
import java.util.HashMap;

import time.SuperFastTime;


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

	public ArrayList<CourseBlock> getAllCourseBlocks() {
		return this.schedule;
	}
	
	public ArrayList<Conflict> findConflicts1() {
		ArrayList<Conflict> conflicts = new ArrayList<>();
		SuperFastTime iStart;
		SuperFastTime iEnd;
		DayOfWeek iDay;
		HashMap<Integer, Boolean> conflictFinder = new HashMap();
		CourseBlock courseBlock;
		for (int i = 0; i < schedule.size(); i++) {
			courseBlock = schedule.get(i);
			iStart = courseBlock.getCourseTime().getBegin();
			iEnd = courseBlock.getCourseTime().getEnd();
			iDay = courseBlock.getCourseTime().getDay();

			if (conflictFinder.get(courseBlock) != null) {
				conflicts.add(new Conflict(// we're just counting instead of
											// adding the conflict courses
						null, null, null));
			} else {
				conflictFinder.put(courseBlock.hashCode(), true);
			}

			if (((iEnd.getHourOfDay() * 60 + iEnd.getMinuteOfHour()) - (iStart
					.getHourOfDay() * 60 + iStart.getMinuteOfHour())) > 90) {
				int newHash;
				if (courseBlock.hashCode() / 10 % 10 == 0) {// course xx:00
					newHash = courseBlock.getCourseTime().getDay()
							.getValue()
							* 10000
							+ (courseBlock.getCourseTime().getBegin()
									.getHourOfDay() + 1) * 100 + 30;
				} else {// course xx:30
					newHash = courseBlock.getCourseTime().getDay()
							.getValue()
							* 10000
							+ (courseBlock.getCourseTime().getBegin()
									.getHourOfDay() + 2) * 100 + 00;
				}

				if (conflictFinder.get(newHash) != null)
					conflicts.add(new Conflict(// we're just counting instead of
												// adding the conflict courses
							null, null, null));
				else
					conflictFinder.put(newHash, true);
			}
		}
		return conflicts;
	}
	
	public ArrayList<Conflict> findConflicts() {
		ArrayList<Conflict> conflicts = new ArrayList<>();
		SuperFastTime iStart;
		SuperFastTime iEnd;
		SuperFastTime jStart;
		SuperFastTime jEnd;
		DayOfWeek iDay;
		DayOfWeek jDay;

		for (int i = 0; i<schedule.size();i++){
			iStart = schedule.get(i).getCourseTime().getBegin();
			iEnd = schedule.get(i).getCourseTime().getEnd();
			iDay = schedule.get(i).getCourseTime().getDay();
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
