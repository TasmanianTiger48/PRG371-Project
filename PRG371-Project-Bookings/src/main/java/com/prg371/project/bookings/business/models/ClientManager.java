package com.prg371.project.bookings.business.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RGottsche
 */
import java.util.HashMap;
import java.util.Map;

public class ClientManager {
    private Map<String, Client> clients = new HashMap<>();

    public Client registerClient(String clientID, String name, String surname, String phoneNumber, String email) {
        Client client = new Client(clientID, name, surname, phoneNumber, email);
        clients.put(clientID, client);
        return client;
    }

    public Client updateClient(String clientID, String newName, String newPhoneNumber) {
        Client client = clients.get(clientID);
        if (client != null) {
            client.setName(newName);
            client.setPhoneNumber(newPhoneNumber);
        }
        return client;
    }
}

