/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 *
 * @author User
 */
public class UserModel {
    private int userId;
    private String name;
    private String email;
    private byte[] passwordHash;
    private LocalDateTime createdAt;

    // Constructors, getters, and setters
    public UserModel() {}

    public UserModel(int userId, String name, String email, byte[] passwordHash, LocalDateTime createdAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setPassword(String password) {
        this.passwordHash = hashPassword(password);
    }
    
    public boolean checkPassword(String password) {
        byte[] hashedInput = hashPassword(password);
        return Arrays.equals(hashedInput, this.passwordHash);
    }
    
    private byte[] hashPassword(String plainPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(plainPassword.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing algorithm not found: " + e.getMessage());
        }
    }
}