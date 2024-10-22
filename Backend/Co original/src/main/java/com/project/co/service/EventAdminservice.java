package com.project.co.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.dto.EventAdmindto;
import com.project.co.entity.EventAdmin;
import com.project.co.repo.EventAdminsrepo;

@Service
@Transactional
public class EventAdminservice {
    @Autowired
    private EventAdminsrepo eventadminsrepo;
    @Autowired
    private ModelMapper modelMapper;

    public EventAdmindto saveEvent(EventAdmindto eventadmindto){

        String dateString =eventadmindto.getEventDate();

        //String dateString = "24-7-2024";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");

        // Step 3: Parse the date string to LocalDate
        LocalDate date = LocalDate.parse(dateString, formatter);

        // Step 4: Extract day, month, and year
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();

        eventadmindto.setEventDate(year+"/"+month+"/"+day);

        eventadminsrepo.save(modelMapper.map(eventadmindto, EventAdmin.class));
        return eventadmindto;
    }

    public List<EventAdmindto> findAllEventsByAdminId(String adminId) {
        List<EventAdmin> events = eventadminsrepo.findAllEventsByAdminId(adminId);

        // Convert entities to DTOs
        return events.stream()
                .map(event -> new EventAdmindto(event.getEventId(), event.getEventName(), event.getEventDescription(), event.getEventDate(),event.getAdmin_id()))
                .collect(Collectors.toList());
    }




}
