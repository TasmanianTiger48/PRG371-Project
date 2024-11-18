/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.dataaccess.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import prg371.project.bookings.business.enums.UserTypes;
import prg371.project.bookings.business.models.UserModel;
import prg371.project.bookings.dataaccess.ConnectionProvider;

/**
 *
 * @author User
 */
public class UserRepository {
    public boolean addUser(UserModel user) {
        String query = "INSERT INTO Users (Name, Surname, ContactNumber, Email, PasswordHash, CreatedAt, Type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getContactNumber());
            statement.setString(4, user.getEmail());
            statement.setBytes(5, user.getPasswordHash());
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(7, user.getType().getKey());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserModel getUserById(int userId) {
        String query = "SELECT * FROM Users WHERE Id = ?";

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRowToUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private UserModel mapRowToUser(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("Id");
        String username = resultSet.getString("Name");
        String surname = resultSet.getString("Surname");
        String contactNumber = resultSet.getString("ContactNumber");
        String email = resultSet.getString("Email");
        byte[] passwordHash = resultSet.getBytes("PasswordHash");
        Timestamp createdAtTimestamp = resultSet.getTimestamp("CreatedAt");
        LocalDateTime createdAt = createdAtTimestamp.toLocalDateTime();
        UserTypes type = UserTypes.fromKey(resultSet.getInt("Type"));

        return new UserModel(userId, username, surname, contactNumber, email, passwordHash, createdAt, type);
    }
    
    public UserModel getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email = ?";

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRowToUser(resultSet);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return null;
    }

    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        String query = "SELECT * FROM Users";

        try (Connection connection = ConnectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                users.add(mapRowToUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
