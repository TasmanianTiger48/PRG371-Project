/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.services;

import java.util.List;
import prg371.project.bookings.business.models.EventTypeModel;
import prg371.project.bookings.dataaccess.repositories.EventTypeRepository;

/**
 *
 * @author User
 */
public class EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    
    public EventTypeService() {
        this.eventTypeRepository = new EventTypeRepository();
    }
        
    public boolean addEventType(EventTypeModel event) {
        EventTypeModel existingEvent = eventTypeRepository.getEventTypeByDescription(event.getDescription());
        if (existingEvent != null) {
            if (!existingEvent.getIsActive()) {
                event.setId(existingEvent.getId());
                return eventTypeRepository.updateEventType(event);
            }
            return false;
        }
        return eventTypeRepository.addEventType(event);
    }
    
    public boolean updateEventType(EventTypeModel event) {
        return eventTypeRepository.updateEventType(event);
    }
    
    public boolean removeEventType(int id) {
        return eventTypeRepository.removeEventType(id);
    }
    
    public List<EventTypeModel> getEventTypes() {
        return eventTypeRepository.getActiveEventTypes();
    }
}
