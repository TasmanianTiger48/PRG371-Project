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
import prg371.project.bookings.business.enums.MenuItemCategoryTypes;
import prg371.project.bookings.business.models.MenuItemModel;
import prg371.project.bookings.dataaccess.ConnectionProvider;

/**
 *
 * @author User
 */
public class MenuItemRepository {
    
    public boolean addMenuItem(MenuItemModel menuItem) {
        String query = "INSERT INTO MenuItems (Name, Description, CategoryType, Price, IsActive) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, menuItem.getName());
            statement.setString(2, menuItem.getDescription());
            statement.setInt(3, menuItem.getCategoryType().getKey());
            statement.setDouble(4, menuItem.getPrice());
            statement.setBoolean(5, menuItem.getIsActive());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMenuItem(MenuItemModel menuItem) {
        String query = "UPDATE MenuItems SET Name = ?, Description = ?, CategoryType = ?, Price = ? WHERE Id = ?";

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, menuItem.getName());
            statement.setString(2, menuItem.getDescription());
            statement.setInt(3, menuItem.getCategoryType().getKey());
            statement.setDouble(4, menuItem.getPrice());
            statement.setInt(5, menuItem.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public MenuItemModel getMenuItemById(int id) {
        String query = "SELECT * FROM MenuItems WHERE Id = ?";

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRowToMenuItem(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public MenuItemModel getMenuItemByName(String name) {
        String query = "SELECT * FROM MenuItems WHERE Name = ?";

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRowToMenuItem(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<MenuItemModel> getActiveMenuItems() {
        List<MenuItemModel> menuItems = new ArrayList<>();
        String query = "SELECT * FROM MenuItems WHERE IsActive = TRUE";

        try (Connection connection = ConnectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                menuItems.add(mapRowToMenuItem(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    public boolean removeMenuItem(int id) {
        String query = "UPDATE MenuItems SET IsActive = FALSE WHERE Id = ?";

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private MenuItemModel mapRowToMenuItem(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Id");
        String name = resultSet.getString("Name");
        String description = resultSet.getString("Description");
        MenuItemCategoryTypes categoryType = MenuItemCategoryTypes.fromKey(resultSet.getInt("CategoryType"));
        Double price = resultSet.getDouble("Price");
        Boolean isActive = resultSet.getBoolean("IsActive");
        
        return new MenuItemModel(id, name, description, categoryType, price, isActive);
    }
}
