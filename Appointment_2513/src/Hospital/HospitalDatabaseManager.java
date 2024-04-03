package Hospital;
import java.sql.*;

public class HospitalDatabaseManager {
    private Connection conn;
    
    public HospitalDatabaseManager(String url, String user, String password) throws SQLException {
        
        this.conn = DriverManager.getConnection(url, user, password);
    }

    public void createTables() throws SQLException {
        
        String createPatientsTable = "CREATE TABLE IF NOT EXISTS Patients (" +
                "patient_id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "age INT," +
                "contact_info VARCHAR(255)," +
                "symptoms VARCHAR(255)" +
                ");";

        String createDoctorsTable = "CREATE TABLE IF NOT EXISTS Doctors (" +
                "doctor_id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "specialization VARCHAR(255)," +
                "contact_info VARCHAR(255)" +
                ");";

        String createAppointmentsTable = "CREATE TABLE IF NOT EXISTS Appointments (" +
                "appointment_id INT AUTO_INCREMENT PRIMARY KEY," +
                "patient_id INT," +
                "doctor_id INT," +
                "appointment_date DATE," +
                "appointment_time TIME," +
                "reason VARCHAR(255)," +
                "status VARCHAR(255)," +
                "FOREIGN KEY (patient_id) REFERENCES Patients(patient_id)," +
                "FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id)" +
                ");";

        
        Statement stmt = conn.createStatement();
        stmt.execute(createPatientsTable);
        stmt.execute(createDoctorsTable);
        stmt.execute(createAppointmentsTable);
        stmt.close();

        
    }
    public void displayAllTables() throws SQLException {
    	 System.out.println("\t\t\t\tDisplaying Tables :");
         System.out.println("\t\t\t\t-------------------\n");

        displayPatients();
        displayDoctors();
        displayAppointments();
        System.out.println("\t\t\t\t-------------------\n");

    }
    public void insertPatient(String name, int age, String contactInfo, String symptoms) throws SQLException {
        String insertSQL = "INSERT INTO Patients (name, age, contact_info, symptoms) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setString(1, name);
        pstmt.setInt(2, age);
        pstmt.setString(3, contactInfo);
        pstmt.setString(4, symptoms);
        pstmt.executeUpdate();
        pstmt.close();

        
        System.out.println("\t\t\t\tPatient Inserted :");
        System.out.println("\t\t\t\t-------------------\n");

        displayPatients();
        System.out.println("\t\t\t\t-------------------\n");

    }
    public void insertDoctor(String name, String specialization, String contactInfo) throws SQLException {
        String insertSQL = "INSERT INTO Doctors (name, specialization, contact_info) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setString(1, name);
        pstmt.setString(2, specialization);
        pstmt.setString(3, contactInfo);
        pstmt.executeUpdate();
        pstmt.close();

        
        System.out.println("\t\t\t\tDoctor Inserted :");
        System.out.println("\t\t\t\t-------------------\n");

        displayDoctors();
        System.out.println("\t\t\t\t-------------------\n");

    }

    public void insertAppointment(int patientId, int doctorId, Date appointmentDate, Time appointmentTime, String reason, String status) throws SQLException {
        String insertSQL = "INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time, reason, status) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setInt(1, patientId);
        pstmt.setInt(2, doctorId);
        pstmt.setDate(3, appointmentDate);
        pstmt.setTime(4, appointmentTime);
        pstmt.setString(5, reason);
        pstmt.setString(6, status);
        pstmt.executeUpdate();
        pstmt.close();

        
        System.out.println("\t\t\t\tAppointments Inserted :");
        System.out.println("\t\t\t\t-------------------\n");

        displayAppointments();
        System.out.println("\t\t\t\t-------------------\n");

    }

    public void updateDoctorContactInfo(int doctorId, String newContactInfo) throws SQLException {
        String updateSQL = "UPDATE Doctors SET contact_info = ? WHERE doctor_id = ? " ;
        PreparedStatement pstmt = conn.prepareStatement(updateSQL);
        pstmt.setString(1, newContactInfo);
        pstmt.setInt(2, doctorId);
      //  pstmt.setString(1, name);
        pstmt.executeUpdate();
        pstmt.close();

        
        System.out.println("\t\t\t\tDoctor Updated :");
        System.out.println("\t\t\t\t-------------------\n");

        displayDoctors();
        System.out.println("\t\t\t\t-------------------\n");

    }

    public void cancelAppointment(int appointmentId) throws SQLException {
        String updateSQL = "UPDATE Appointments SET status = 'Cancelled' WHERE appointment_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(updateSQL);
        pstmt.setInt(1, appointmentId);
        pstmt.executeUpdate();
        pstmt.close();

        
        System.out.println("\t\t\t\tCancel Appointment (Updated) :");
        System.out.println("\t\t\t\t-------------------\n");

        displayAppointments();
        System.out.println("\t\t\t\t-------------------\n");

    }
    

    public void displayPatients() throws SQLException {
        System.out.println("Patients Table:");
        String query = "SELECT * FROM Patients";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("patient_id") + ", Name: " + rs.getString("name") + ", Age: " + rs.getInt("age") + ", Contact Info: " + rs.getString("contact_info") + ", Symptoms: " + rs.getString("symptoms"));
        }
        System.out.println();
    }

    public void displayDoctors() throws SQLException {
        System.out.println("Doctors Table:");
        String query = "SELECT * FROM Doctors";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("doctor_id") + ", Name: " + rs.getString("name") + ", Specialization: " + rs.getString("specialization") + ", Contact Info: " + rs.getString("contact_info"));
        }
        System.out.println();
    }

    public void displayAppointments() throws SQLException {
        System.out.println("Appointments Table:");
        String query = "SELECT * FROM Appointments";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("appointment_id") + ", Patient ID: " + rs.getInt("patient_id") + ", Doctor ID: " + rs.getInt("doctor_id") + ", Date: " + rs.getDate("appointment_date") + ", Time: " + rs.getTime("appointment_time") + ", Reason: " + rs.getString("reason") + ", Status: " + rs.getString("status"));
        }
        System.out.println();
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public static void main(String[] args) {
    	String url = "jdbc:mysql://localhost:3306/hospital";
        String username = "root";
        String password = "i2269910$";
        try {
            HospitalDatabaseManager dbManager = new HospitalDatabaseManager(url, username, password);
            dbManager.createTables();
            System.out.println("\n\nPATIENTS , APPOINTMENT , DOCTORS TABLES ARE CREATED :\n\n");
            dbManager.displayAllTables();
            dbManager.insertPatient("Will", 35, "contact details", "Persistent headaches");
            dbManager.insertDoctor("Dr. Lecter", "Neurologist", "contact details");
            dbManager.insertAppointment(1, 1, Date.valueOf("2024-03-25"), Time.valueOf("14:00:00"), "Headache consultation", "Scheduled");
            dbManager.updateDoctorContactInfo(1 ,"new contact info");
            dbManager.insertDoctor("Dr Bloom", "Neurologist", "contact details");
            
           // dbManager.displayAllTables();
            dbManager.cancelAppointment(1);
           // dbManager.clearAllTablesData();
            dbManager.displayAllTables();
            dbManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}