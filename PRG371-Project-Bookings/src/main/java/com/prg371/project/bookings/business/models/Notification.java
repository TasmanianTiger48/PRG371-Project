package com.prg371.project.bookings.business.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RGottsche
 */
public class Notification {
    private String notificationID;
    private String message;
    private String clientID;

    public Notification(String message, String clientID) {
        this.notificationID = notificationID;
        this.message = message;
        this.clientID = clientID;
    }

    // Getters and Setters
    public String getNotificationID() { return notificationID; }
    public void setNotificationID(String notificationID) { this.notificationID = notificationID; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getClientID() { return clientID; }
    public void setClientID(String clientID) { this.clientID = clientID; }
}

