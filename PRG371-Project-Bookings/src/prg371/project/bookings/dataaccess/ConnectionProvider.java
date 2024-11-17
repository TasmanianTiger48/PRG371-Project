/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author User
 */
public class ConnectionProvider {
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String JDBC_URL = "jdbc:derby:PRG371ProjectBookingDB; create=true";
    Connection con;
    
    public ConnectionProvider() {
    }
    
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Derby driver not found.", e);
        }
        return DriverManager.getConnection(JDBC_URL);
    }
    
    public void connect() throws ClassNotFoundException {
        try {
            Class.forName(DRIVER);
            this.con = DriverManager.getConnection(JDBC_URL);
            if (this.con != null) {
                System.out.println("Connected to Database");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to Connect to Database");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to Connect to Database");
            ex.printStackTrace();
        }
    }
    
    public void generateDB() {
        executeSQLQuery("DROP TABLE Users");
        executeSQLQuery("DROP TABLE EventTypes");
        executeSQLQuery("DROP TABLE MenuItems");
        
        executeSQLQuery(
            "CREATE TABLE Users (" +
                "Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                "Name VARCHAR(50) NOT NULL, " +
                "Email VARCHAR(100) NOT NULL, " +
                "PasswordHash BLOB, " +
                "CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")"
        );
        
        executeSQLQuery(
            "CREATE TABLE EventTypes (" +
                "Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                "Description VARCHAR(50) NOT NULL," +
                "BaseAmount DECIMAL(10, 2) NOT NULL," +
                "IsActive BOOLEAN NOT NULL" +
            ")"
        );
        
        executeSQLQuery(
            "CREATE TABLE MenuItems (" +
                "Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                "Name VARCHAR(100) NOT NULL," +
                "Description VARCHAR(255)," +
                "CategoryType INT NOT NULL," +
                "Price DECIMAL(10, 2) NOT NULL," +
                "IsActive BOOLEAN NOT NULL" +
            ")"
        );
    }
    
    private void executeSQLQuery(String query) {
        try {
            this.con.createStatement().execute(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close the database connection: " + e.getMessage());
            }
        }
    }
}
