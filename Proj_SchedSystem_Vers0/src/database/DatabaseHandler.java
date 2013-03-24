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
		databaseSave(new ArrayList<>(Run09Generator.generateClassList(10).values()));
	}
	
	private static void databaseSave(ArrayList<String> courses){
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
}
