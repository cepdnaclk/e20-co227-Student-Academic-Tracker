package com.project.co.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class EventAdmindto {

    private Long eventId;
    private String Admin_id;
    private String eventName;
    private String eventDescription;
    private String eventDate;

    public EventAdmindto(Long eventId, String eventName, String eventDescription, String eventDate,String Admin_id) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.Admin_id = Admin_id;
    }
}
