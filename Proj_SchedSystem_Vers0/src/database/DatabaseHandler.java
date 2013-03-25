package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import runs.Run09Generator;

public class DatabaseHandler {
	public static void main(String[] args) {
		databaseSaveCourses(new ArrayList<>(Run09Generator.generateClassList(10).values()));
	}
	
	public static void databaseSaveCourses(ArrayList<String> courses){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Connection connection = null;
		try {		
			connection = DriverManager.getConnection("jdbc:sqlite:database.db");
			
			Statement statement = connection.createStatement();

			statement.setQueryTimeout(20);
			
			statement.executeUpdate("drop table if exists courselist");
			statement.executeUpdate("create table courselist (name string)");
			
			for (String course: courses){
				statement.executeUpdate("insert into courselist values('"+ course +"')");
			}
			
			ResultSet rs = statement.executeQuery("select * from courselist");
			
			while (rs.next()){
				System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<String> databaseloadCourses(){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Connection connection = null;
		try {		
			connection = DriverManager.getConnection("jdbc:sqlite:database.db");
			
			Statement statement = connection.createStatement();

			statement.setQueryTimeout(20);
			
			ResultSet rs = statement.executeQuery("select name from courselist");
			
			ArrayList<String> courses = new ArrayList<>();
			
			while (rs.next()){
				courses.add(rs.getString(1));
			}
			
			return courses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return new ArrayList<>();
	}
	
}
