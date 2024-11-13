package com.prg371.project.bookings.business.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author RGottsche
 */
public interface INotificationService {
    void sendConfirmation(Client client, Booking booking);

    void sendUpdateNotification(Client client, String updateDetails);
}

