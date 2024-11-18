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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import prg371.project.bookings.business.enums.MenuItemCategoryTypes;
import prg371.project.bookings.business.models.EventTypeModel;
import prg371.project.bookings.business.models.MenuItemModel;
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
    
    public boolean addLinkedMenuItem(int eventTypeId, int menuItemId) {
        String query = "INSERT INTO EventTypeMenuItems (EventTypeId, MenuItemId) VALUES (?, ?)";

        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, eventTypeId);
            statement.setInt(2, menuItemId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean removeLinkedMenuItem(int eventTypeId, int menuItemId) {
        String query = "DELETE EventTypeMenuItems WHERE EventTypeId = ? AND MenuItemId = ?";

        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, eventTypeId);
            statement.setInt(2, menuItemId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Integer getLinkedMenuItemIdByIds(int eventTypeId, int menuItemId) {
        String query = "SELECT Id FROM EventTypeMenuItems WHERE EventTypeId = ? AND MenuItemId = ?";

        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, eventTypeId);
            statement.setInt(2, menuItemId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public Map<EventTypeModel, List<MenuItemModel>> getActiveEventTypeMenuItems() {
        Map<EventTypeModel, List<MenuItemModel>> eventTypeMap = new HashMap<>();
        
        String query = """
        SELECT et.Id AS EventTypeId, et.Description AS EventTypeDescription, et.BaseAmount, et.IsActive AS EventTypeActive,
                mi.Id AS MenuItemId, mi.Name AS MenuItemName, mi.Description AS MenuItemDescription, 
                mi.CategoryType, mi.Price, mi.IsActive AS MenuItemActive
            FROM EventTypes et
            LEFT JOIN EventTypeMenuItems etmi ON et.Id = etmi.EventTypeId
            LEFT JOIN MenuItems mi ON etmi.MenuItemId = mi.Id
            WHERE et.IsActive = TRUE AND (mi.IsActive = TRUE OR mi.IsActive IS NULL)
        """;
        
        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int eventTypeId = resultSet.getInt("EventTypeId");
                String eventTypeDescription = resultSet.getString("EventTypeDescription");
                double baseAmount = resultSet.getDouble("BaseAmount");
                boolean isActiveEventType = resultSet.getBoolean("EventTypeActive");

                EventTypeModel eventType = new EventTypeModel(eventTypeId, eventTypeDescription, baseAmount, isActiveEventType);
                eventTypeMap.putIfAbsent(eventType, new ArrayList<>());

                // Extract MenuItem fields (can be null if no MenuItems are linked)
                int menuItemId = resultSet.getInt("MenuItemId");
                if (!resultSet.wasNull()) { // Check if MenuItem exists
                    String menuItemName = resultSet.getString("MenuItemName");
                    String menuItemDescription = resultSet.getString("MenuItemDescription");
                    int categoryType = resultSet.getInt("CategoryType");
                    double price = resultSet.getDouble("Price");
                    boolean isActiveMenuItem = resultSet.getBoolean("MenuItemActive");

                    MenuItemModel menuItem = new MenuItemModel(menuItemId, menuItemName, menuItemDescription, MenuItemCategoryTypes.fromKey(categoryType), price, isActiveMenuItem);
                    eventTypeMap.get(eventType).add(menuItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventTypeMap;
    }
    
}
