package com.prg371.project.bookings.business.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RGottsche
 */
public class Client extends Person {
    private String clientID;
    private String surname;

    public Client(String clientID, String name, String surname, String phoneNumber, String email) {
        super(name, phoneNumber, email);
        this.clientID = clientID;
        this.surname = surname;
    }

    // Getters and Setters
    public String getClientID() { return clientID; }
    public void setClientID(String clientID) { this.clientID = clientID; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
}


