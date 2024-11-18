/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.enums;

/**
 *
 * @author User
 */
public enum MenuItemCategoryTypes {
    AdultMeal(0, "Adult Menu"),
    KidMeal(1, "Kid Menu"),
    Drink(2, "Drink"),
    Dessert(3, "Dessert");
    
    private final int key;
    private final String description;
    
    MenuItemCategoryTypes(int key, String description) {
        this.key = key;
        this.description = description;
    }
    
    public int getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
    
    public static MenuItemCategoryTypes fromKey(int key) {
        for (MenuItemCategoryTypes type : values()) {
            if (type.getKey() == key) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid key provided for MenuItemCategoryTypes: " + key);
    }
    
    public static MenuItemCategoryTypes fromDescription(String desc) {
        for (MenuItemCategoryTypes type : values()) {
            if (type.getDescription().equals(desc)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid description provided for MenuItemCategoryTypes: " + desc);
    }
    
}
