/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.presentation.controllers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import prg371.project.bookings.business.models.UserModel;
import prg371.project.bookings.business.services.UserService;
import prg371.project.bookings.presentation.frames.AdminFrame;
import prg371.project.bookings.presentation.frames.MainFrame;

/**
 *
 * @author User
 */
public class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public Boolean handleRegister(String email, String password, String confirmPassword, String name) {
        
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter all required fields");
            return false;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Password and Confirm Password need to match");
            return false;
        }

        UserModel user = new UserModel();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        if (userService.register(user)) {
            JOptionPane.showMessageDialog(null, "Registration successful");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Registration failed, email may already be in use");
            return false;
        }
    }
    
    public void handleLogin(JFrame loginFrame, String email, String password) {

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter password and email");
            return;
        }
        
        if (!userService.login(email, password)) {
            JOptionPane.showMessageDialog(null, "Login failed, email or password may be incorrect");
        } else {
            new MainFrame().setVisible(true);
            new AdminFrame().setVisible(true);
            loginFrame.dispose();
        }
    }
}
