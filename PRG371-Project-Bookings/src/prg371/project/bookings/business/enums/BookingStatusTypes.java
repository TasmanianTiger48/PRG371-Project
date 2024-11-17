/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.enums;

/**
 *
 * @author User
 */
public enum BookingStatusTypes {
    Pending(1, "Pending"),
    Confirmed(2, "Confirmed"),
    Cancelled(3, "Cancelled");
    
    private final int key;
    private final String description;
    
    BookingStatusTypes(int key, String description) {
        this.key = key;
        this.description = description;
    }
    
    public int getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
    
    public static BookingStatusTypes fromKey(int key) {
        for (BookingStatusTypes type : values()) {
            if (type.getKey() == key) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid key provided for MenuItemCategoryTypes: " + key);
    }
    
    public static BookingStatusTypes fromDescription(String desc) {
        for (BookingStatusTypes type : values()) {
            if (type.getDescription().equals(desc)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid description provided for MenuItemCategoryTypes: " + desc);
    }
}
