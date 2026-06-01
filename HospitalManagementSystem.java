package hms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;

public class HospitalManagementSystem {
	
	private static final String url = "jdbc:mysql://localhost:3306/hms";
	private static final String username = "root";
	private static final String password = "HappyLearning";
	
	public static void main(String[] args)
	{
	try {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
	}	catch(ClassNotFoundException e)
	{
		e.printStackTrace();
		
	}
	
	Scanner scanner = new Scanner(System.in);
	
	try {
		
		Connection connection = DriverManager.getConnection(url,username,password);
		Patient patient = new Patient(connection,scanner);
		Doctor doctor = new Doctor(connection);
		while(true)
		{
			System.out.println("Hospital Management System");
			System.out.println("1.Add Patient");
			System.out.println("2.View Patients");
			System.out.println("3.View Doctors");
			System.out.println("4.Book Appoinment");
			System.out.println("5.Exit");
			System.out.println("Please Enter Your Choice");
			
			int choice = scanner.nextInt();
			
			switch(choice)
			{
			
			case 1 :
				// Add Patient
				patient.addPatient();
		        System.out.println();
                break;
        	case 2 :
				// View Patient
				patient.viewPatients();
		        System.out.println();
                break;        
			case 3 :
				// View Doctors
				doctor.viewDoctors();
		        System.out.println();
                break;        
			case 4 :
				//Book Appointment
				bookAppointment(patient, doctor, connection, scanner);
                System.out.println();
                break;           
			case 5 :
			    System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                return;
            default :
				System.out.println("Please Enter Valid Input");
				break;
				
			}
		}
		
	}catch(SQLException e)
	{
		e.printStackTrace();
	}
		
	}
	
	public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner)
	{
		System.out.println("Please Enter Patient ID:");
		int patientID = scanner.nextInt();
		System.out.println("Please Enter Doctor ID:");
		int doctorID = scanner.nextInt();
		System.out.println("Please Enter Appointment Date(YYYY-MM-DD): ");
		String appointmentDate = scanner.next();
		
		if(patient.getPatientByID(patientID) && doctor.getDoctorByID(doctorID))
		{
			if(checkDoctorAvailability(doctorID,appointmentDate,connection)) {
			String appointmentQuery = "INSERT INTO appointments (patient_id,doctor_id,appointment_date) values(?,?,?)";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
				preparedStatement.setInt(1,patientID);
				preparedStatement.setInt(2,doctorID);
				preparedStatement.setString(3,appointmentDate);
				
				int rowsAffected = preparedStatement.executeUpdate();
				
				if(rowsAffected>0)
				{
					System.out.println("Appointment Booked");
				}
				else
				{
					System.out.println("Appointment could not be booked");
				}
				}catch(SQLException e)
			{
					e.printStackTrace();}
			}
			else{
                System.out.println("Doctor not available on this date!!");
            }
        }else{
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
    }
	
	public static boolean checkDoctorAvailability(int doctorID,String appointmentDate,Connection connection)
	{
		//Count(*) : getting the rows which matches the particular criteria
		String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id =? AND appointment_date = ?";
		try {
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1,doctorID);
		preparedStatement.setString(2,appointmentDate);
		
		ResultSet resultset = preparedStatement.executeQuery();
		
		if(resultset.next()) {
			
			int count = resultset.getInt(1);
			
			if(count == 0)
			{
			return true;
		}
			else {
			return false;	
			}
			}
		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	{
		
	}

}