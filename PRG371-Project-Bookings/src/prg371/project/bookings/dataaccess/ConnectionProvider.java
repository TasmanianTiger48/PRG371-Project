/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import prg371.project.bookings.business.enums.UserTypes;
import prg371.project.bookings.business.models.UserModel;
import prg371.project.bookings.dataaccess.repositories.UserRepository;
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
        executeSQLQuery("DROP TABLE BookingMenuItems");
        executeSQLQuery("DROP TABLE Bookings");
        executeSQLQuery("DROP TABLE Users");
        executeSQLQuery("DROP TABLE EventTypeMenuItems");
        executeSQLQuery("DROP TABLE EventTypes");
        executeSQLQuery("DROP TABLE MenuItems");
        
        executeSQLQuery(
            "CREATE TABLE Users (" +
                "Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                "Name VARCHAR(50) NOT NULL, " +
                "Email VARCHAR(100) NOT NULL, " +
                "PasswordHash BLOB, " +
                "CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "Type INT NOT NULL" +
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
        
        executeSQLQuery(
            "CREATE TABLE EventTypeMenuItems (" +
                "Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                "MenuItemId INT NOT NULL," +
                "EventTypeId INT NOT NULL," +
                "FOREIGN KEY (MenuItemId) REFERENCES MenuItems(Id)," +
                "FOREIGN KEY (EventTypeId) REFERENCES EventTypes(Id)" +
            ")"
        );
        
        executeSQLQuery(
            "CREATE TABLE Bookings (" +
                "Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                "EventTypeId INT NOT NULL," +
                "DecorateOptIn BOOLEAN," +
                "EventDate DATE NOT NULL," +
                "VenueAddress VARCHAR(255)," +
                "Adults INT NOT NULL," +
                "Children INT NOT NULL," +
                "Status INT NOT NULL," +
                "CreatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "LastUpdateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "UserId INT NOT NULL," +
                "CalculatedPrice DECIMAL(10, 2) NOT NULL," +
                "FOREIGN KEY (UserId) REFERENCES Users(Id)," +
                "FOREIGN KEY (EventTypeId) REFERENCES EventTypes(Id)" +
            ")"
        );
        
        executeSQLQuery(
            "CREATE TABLE BookingMenuItems (" +
                "Id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                "BookingId INT NOT NULL," +
                "MenuItemId INT NOT NULL," +
                "Amount INT NOT NULL," +
                "FOREIGN KEY (MenuItemId) REFERENCES MenuItems(Id)," +
                "FOREIGN KEY (BookingId) REFERENCES Bookings(Id)" +
            ")"
        );
    }
    
    public void seedDB() {
        executeSQLQuery(
            "INSERT INTO EventTypes (Description, BaseAmount, IsActive) VALUES " +
            "('Birthday Party', 500.00, TRUE), " +
            "('Wedding', 1200.00, TRUE), " +
            "('Corporate Meeting', 800.00, TRUE), " +
            "('Workshop', 300.00, TRUE), " +
            "('Music Festival', 1500.00, FALSE), " +
            "('Charity Event', 1000.00, TRUE), " +
            "('Anniversary Celebration', 750.00, TRUE)"
        );
        
        executeSQLQuery(
            "INSERT INTO MenuItems (Name, Description, CategoryType, Price, IsActive) VALUES " +
            "('Cheeseburger', 'A classic cheeseburger with cheddar cheese', 0, 5.99, TRUE), " +
            "('Veggie Burger', 'A healthy vegetarian burger option', 0, 6.49, TRUE), " +
            "('Fries', 'Crispy golden french fries', 1, 2.99, TRUE), " +
            "('Caesar Salad', 'Fresh romaine lettuce with Caesar dressing', 2, 7.99, TRUE), " +
            "('Soda', 'Refreshing carbonated soft drink', 3, 1.99, TRUE), " +
            "('Steak', 'Grilled ribeye steak with a side of vegetables', 0, 24.99, FALSE), " +
            "('Chocolate Cake', 'Rich chocolate cake with creamy frosting', 3, 4.99, TRUE)"
        );
        
        executeSQLQuery(
            "INSERT INTO EventTypeMenuItems (MenuItemId, EventTypeId) VALUES " +
            "(1, 1), " +
            "(2, 1), " +
            "(3, 2), " +
            "(4, 2), " +
            "(5, 3), " +
            "(1, 3), " +
            "(6, 4), " +
            "(7, 5) "
        );
        
        UserRepository userRepository = new UserRepository();
        userRepository.addUser(new UserModel("a", "a", "a", UserTypes.Admin));
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
