/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.enums;

/**
 *
 * @author User
 */
public enum NotificationTypes {
    MenuChanged(1, "The menu has been updated for your booking."),
    BookingConfirmed(2, "Booking has been confirmed."),
    BookingCanceled(3, "Booking has been canceled."),
    BookingConfirmationRequired(4, "Booking requires confirmation.");

    private final int key;
    private final String message;

    NotificationTypes(int key, String message) {
        this.key = key;
        this.message = message;
    }

    public int getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public static NotificationTypes fromKey(int key) {
        for (NotificationTypes type : values()) {
            if (type.getKey() == key) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid key provided for NotificationTypes: " + key);
    }
}
