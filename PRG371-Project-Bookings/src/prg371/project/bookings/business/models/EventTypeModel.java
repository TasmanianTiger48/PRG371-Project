/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.models;

import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class EventTypeModel {
    
    private int id;
    private String description;
    private Double baseAmount;
    private Boolean isActive;

    public EventTypeModel() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public Double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Double BaseAmount) {
        this.baseAmount = BaseAmount;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public EventTypeModel(String Description, Double BaseAmount) {
        this.description = Description;
        this.baseAmount = BaseAmount;
        this.isActive = true;
    }
    
    public EventTypeModel(int Id, String Description, Double BaseAmount, Boolean isActive) {
        this.id = Id;
        this.description = Description;
        this.baseAmount = BaseAmount;
        this.isActive = isActive;
    }
    
    public String validate() {
        if (this.description == null || this.description.isEmpty() || this.baseAmount == null) {
            return "Please enter values for the description and amount fields";
        }
        
        if (this.baseAmount <= 0) {
            return "Please enter a amount greater than 0";
        }
        return null;
    }
    
}
