package runs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import constraint.MustHaveCourse;
import constraint.NoConflicts;
import constraint.PreferedGeneralCourseTime;

import schedule.CourseBlock;
import schedule.CourseTime;
import schedule.DayOfWeek;
import student.Student;

public class Run09Generator { 
	private static ArrayList<Student> savedStudents = null;
	
	public static ArrayList<Student> generateStudents(int amountOfStudents, HashMap<Integer,String> classList){
		if (savedStudents == null){
		ArrayList<Student> students = new ArrayList<>();
		Random random = new Random();
		
		for (int i = 0; i<amountOfStudents;i++){
			HashMap<String,Object> takingClasses =  new HashMap<>();
			Student tempStudent = new Student();
			
			while (takingClasses.size()<5){ //Every student will take 5 classes, randomly selected from class list
				takingClasses.put(classList.get(random.nextInt((Integer)classList.size())),null);
			}
			
			for(String course: takingClasses.keySet()){
				tempStudent.addConstraint(new MustHaveCourse(course));
			}
			
			if (random.nextBoolean())
				tempStudent.addConstraint(new PreferedGeneralCourseTime(8, 30, 17, 30));
			else
				tempStudent.addConstraint(new PreferedGeneralCourseTime(11, 30, 22, 00));
			tempStudent.addConstraint(new NoConflicts());
			students.add(tempStudent);
		}	
		
		savedStudents = students;
		
		return students;}
		else {
			return savedStudents;
		}
	}
	
	public static HashMap<Integer,String> generateClassList(int classListSize){
		HashMap<Integer,String> classList = new HashMap<>();
		
		for (int i = 0; i<classListSize; i++){
			classList.put((Integer)i,("SEG" + (1000 + i)));
		}
		
		return classList;
	}
	
	public static HashMap<String, CourseBlock> generateBlocksBasedClassList(HashMap<Integer,String> classList){		
		HashMap<String, CourseBlock> blocks = new HashMap<>();
		
		for (String course: classList.values()){
			blocks.put(course, new CourseBlock(course, "LEC", "A", null));
			blocks.put(course, new CourseBlock(course, "LAB", "A", null));
		}
		
		return blocks;
	}

	public static CourseTime generateRandomTime(Random rng) {
		return new CourseTime(
				DayOfWeek.getDayOnNumber(rng.nextInt(5)), 
				rng.nextInt(24), 
				rng.nextInt(2)*30, 
				90);
	}
}
