package com.project.co.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class Eventdto {

    private Long eventId;
    private String Student_id;
    private String eventName;
    private String eventDescription;
    private String eventDate;

    public Eventdto(Long eventId, String eventName, String eventDescription, String eventDate,String Student_id) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.Student_id = Student_id;
    }
}
