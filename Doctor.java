package hms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Doctor {

	
	private Connection connection;
	
	
	public Doctor(Connection connection)
	{
		
		this.connection = connection;
		
		
	}
	
	
	//Method to viewPatients
	public void viewDoctors()
	{
		String query = "select * from doctors";
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultset = preparedStatement.executeQuery();
			
			System.out.println("Doctors: ");
			System.out.println("+-------------+---------------------+------+----------+");
			System.out.println(" | Doctor ID  | Name                | Specialization  |");
			System.out.println("+-------------+---------------------+------+----------+");
			
			while(resultset.next())
			{
				
				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				//int age = resultset.getInt("age");
				String specilization = resultset.getString("specialization");
				
				System.out.printf("|%-15s|%-20s|%-18s|\n",id,name,specilization);
				System.out.println("+-------------+---------------------+------+----------+");
			}
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorByID(int id)
	{
		String query = "select * from doctors where ID = ?";
		
		try {
			
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setInt(1,id);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next())
			{
				return true;
				}
			else {
				return false;
				}
			
		}catch(SQLException e) {e.printStackTrace();}
		return false;
	}


	
	}

