package com.prg371.project.bookings.business.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RGottsche
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {

    public void createClient(Client client) throws SQLException {
        String sql = "INSERT INTO Clients (ClientID, Name, Surname, PhoneNumber, Email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, client.getClientID());
            pstmt.setString(2, client.getName());
            pstmt.setString(3, client.getSurname());
            pstmt.setString(4, client.getPhoneNumber());
            pstmt.setString(5, client.getEmail());
            pstmt.executeUpdate();
        }
    }

    public Client getClient(String clientID) throws SQLException {
        String sql = "SELECT * FROM Clients WHERE ClientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clientID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Client(
                    rs.getString("ClientID"),
                    rs.getString("Name"),
                    rs.getString("Surname"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Email")
                );
            }
            return null;
        }
    }

    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Clients";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new Client(
                    rs.getString("ClientID"),
                    rs.getString("Name"),
                    rs.getString("Surname"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Email")
                ));
            }
        }
        return clients;
    }

    public void updateClient(Client client) throws SQLException {
        String sql = "UPDATE Clients SET Name = ?, Surname = ?, PhoneNumber = ?, Email = ? WHERE ClientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getSurname());
            pstmt.setString(3, client.getPhoneNumber());
            pstmt.setString(4, client.getEmail());
            pstmt.setString(5, client.getClientID());
            pstmt.executeUpdate();
        }
    }

    public void deleteClient(String clientID) throws SQLException {
        String sql = "DELETE FROM Clients WHERE ClientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clientID);
            pstmt.executeUpdate();
        }
    }
}

