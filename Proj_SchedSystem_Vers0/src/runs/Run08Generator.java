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

public class Run08Generator {
	public static ArrayList<Student> generateStudents(int amountOfStudents, HashMap<Integer,String> classList){
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
		return students;
	}
	
	public static HashMap<Integer,String> generateClassList(int classListSize){
		HashMap<Integer,String> classList = new HashMap<>();
		
		for (int i = 0; i<classListSize; i++){
			classList.put((Integer)i,("SEG" + (1000 + i)));
		}
		
		return classList;
	}
	
	public static ArrayList<CourseBlock> generateBlocksBasedClassList(HashMap<Integer,String> classList){		
		ArrayList<CourseBlock> blocks = new ArrayList<>();
		
		for (String course: classList.values()){
			blocks.add(new CourseBlock(course, "LEC", "A", null));
			blocks.add(new CourseBlock(course, "LAB", "A", null));
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
