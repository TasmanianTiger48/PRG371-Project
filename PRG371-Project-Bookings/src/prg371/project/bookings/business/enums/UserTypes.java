/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.enums;

/**
 *
 * @author User
 */
public enum UserTypes {
    Standard(1),
    Admin(2);
    
    private final int key;
    
    UserTypes(int key) {
        this.key = key;
    }
    
    public int getKey() {
        return key;
    }
    
    public static UserTypes fromKey(int key) {
        for (UserTypes type : values()) {
            if (type.getKey() == key) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid key provided for UserTypes: " + key);
    }
}
