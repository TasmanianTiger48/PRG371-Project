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
import java.util.ArrayList;
import java.util.List;
import prg371.project.bookings.business.models.EventTypeModel;
import prg371.project.bookings.dataaccess.ConnectionProvider;

/**
 *
 * @author User
 */
public class EventTypeRepository {
    public boolean addEventType(EventTypeModel event) {
        String query = "INSERT INTO EventTypes (Description, BaseAmount, IsActive) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, event.getDescription());
            statement.setDouble(2, event.getBaseAmount());
            statement.setBoolean(3, event.getIsActive());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateEventType(EventTypeModel event) {
        String query = "UPDATE EventTypes SET Description = ?, BaseAmount = ?, IsActive = ? WHERE Id = ?";
        
        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, event.getDescription());
            statement.setDouble(2, event.getBaseAmount());
            statement.setBoolean(3, event.getIsActive());
            statement.setInt(4, event.getId());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public EventTypeModel getEventTypeById(int eventId) {
        String query = "SELECT * FROM EventTypes WHERE Id = ?";

        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRowToEventType(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private EventTypeModel mapRowToEventType(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Id");
        String description = resultSet.getString("Description");
        Double amount = resultSet.getDouble("BaseAmount");
        Boolean isActive = resultSet.getBoolean("IsActive");

        return new EventTypeModel(id, description, amount, isActive);
    }
    
    public EventTypeModel getEventTypeByDescription(String description) {
        String sql = "SELECT * FROM EventTypes WHERE Description = ?";

        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRowToEventType(resultSet);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving event type: " + e.getMessage());
        }

        return null;
    }

    public List<EventTypeModel> getActiveEventTypes() {
        List<EventTypeModel> eventTypes = new ArrayList<>();
        String query = "SELECT * FROM EventTypes WHERE IsActive = TRUE";

        try (Connection connection = ConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                eventTypes.add(mapRowToEventType(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventTypes;
    }
    
    public boolean removeEventType(int id) {
        String query = "UPDATE EventTypes SET IsActive = ? WHERE Id = ?";
        
        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setBoolean(1, false);
            statement.setInt(2, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
