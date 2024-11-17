/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.services;

import prg371.project.bookings.Main;
import prg371.project.bookings.business.models.UserModel;
import prg371.project.bookings.dataaccess.repositories.UserRepository;

/**
 *
 * @author User
 */
public class UserService {
    private final UserRepository userRepository;
    
    public UserService() {
        this.userRepository = new UserRepository();
    }
        
    public boolean register(UserModel user) {
        if (userRepository.getUserByEmail(user.getEmail()) != null) {
            return false;
        }
        return userRepository.addUser(user);
    }
    
    public boolean login(String email, String password) {
        UserModel user = userRepository.getUserByEmail(email);
        if (user != null && user.checkPassword(password)) {
            Main.currentUser = user;
            return true;
        }
        return false;
    }
    
    public boolean logout() {
        Main.currentUser = null;
        return true;
    }
}
