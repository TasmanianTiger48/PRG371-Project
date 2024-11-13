package com.prg371.project.bookings.business.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RGottsche
 */
public class NotificationService implements INotificationService {

    @Override
    public void sendConfirmation(Client client, Booking booking) {
        String message = "Hello " + client.getName() + ", your booking (ID: " + booking.getBookingID() +
                         ") has been confirmed!";
        sendNotification(client.getClientID(), message);
    }

    @Override
    public void sendUpdateNotification(Client client, String updateDetails) {
        String message = "Hello " + client.getName() + ", your booking has been updated: " + updateDetails;
        sendNotification(client.getClientID(), message);
    }

    private void sendNotification(String clientID, String message) {
        Notification notification = new Notification(message, clientID);
        System.out.println("Notification sent to " + clientID + ": " + message);
    }
}


