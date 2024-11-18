/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.regex.Pattern;
import prg371.project.bookings.business.enums.UserTypes;

/**
 *
 * @author User
 */
public class UserModel {
    private int userId;
    private String name;
    private String surname;
    private String contactNumber;
    private String email;
    private byte[] passwordHash;
    private LocalDateTime createdAt;
    private UserTypes type = UserTypes.Standard;

    public UserModel() { }
    
    public UserModel(String name, String surname, String contactNumber, String email, String password, UserTypes type) {
        this.name = name;
        this.surname = surname;
        this.contactNumber = contactNumber;
        this.email = email;
        this.passwordHash = hashPassword(password);
        this.type = type;
    }

    public UserModel(int userId, String name, String surname, String contactNumber, String email, byte[] passwordHash, LocalDateTime createdAt, UserTypes type) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.contactNumber = contactNumber;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.type = type;
    }

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
    
    public UserTypes getType() {
        return type;
    }

    public void setType(UserTypes type) {
        this.type = type;
    }
    
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
    
    public String validate() {
        if (name == null || name.isEmpty()
            || email == null || email.isEmpty()
            || surname == null || surname.isEmpty()
            || contactNumber == null || contactNumber.isEmpty()
        ) {   
            return "Please enter all required fields";
        }
        
        String regex = "^[0-9]{10}$";
        if (!Pattern.matches(regex, contactNumber)) {
            return "Enter valid contact number (10 characters long, only numbers)";
        }
        
        return null;
    }
}
