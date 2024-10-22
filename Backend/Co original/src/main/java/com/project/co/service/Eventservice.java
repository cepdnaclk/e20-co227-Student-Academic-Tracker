package com.project.co.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.co.dto.Eventdto;
import com.project.co.entity.Event;
import com.project.co.repo.Eventsrepo;

@Service
@Transactional
public class Eventservice {
    @Autowired
    private Eventsrepo eventsrepo;
    @Autowired
    private ModelMapper modelMapper;

    public Eventdto saveEvent(Eventdto eventdto){

        String dateString =eventdto.getEventDate();

        //String dateString = "24-7-2024";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");

        // Step 3: Parse the date string to LocalDate
        LocalDate date = LocalDate.parse(dateString, formatter);

        // Step 4: Extract day, month, and year
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();

        eventdto.setEventDate(year+"/"+month+"/"+day);

        eventsrepo.save(modelMapper.map(eventdto, Event.class));
        return eventdto;
    }

    public List<Eventdto> findAllEventsByStudentId(String studentId) {
        List<Event> events = eventsrepo.findAllEventsByStudentId(studentId);

        // Convert entities to DTOs
        return events.stream()
                .map(event -> new Eventdto(event.getEventId(), event.getEventName(), event.getEventDescription(), event.getEventDate(),event.getStudent_id()))
                .collect(Collectors.toList());
    }




}
