/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prg371.project.bookings;
import java.awt.EventQueue;
import prg371.project.bookings.business.enums.UserTypes;
import prg371.project.bookings.business.models.UserModel;
import prg371.project.bookings.dataaccess.ConnectionProvider;
import prg371.project.bookings.presentation.frames.UserLoginFrame;
/**
 *
 * @author User
 */
public class Main {
    
    public static ConnectionProvider db = new ConnectionProvider();
    public static UserModel currentUser = null;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserLoginFrame().setVisible(true);
                try {
                    db.connect();
                    db.generateDB();
                    db.seedDB();
                    System.gc();
                }
                catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
}
