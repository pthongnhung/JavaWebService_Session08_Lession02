package org.example.javawebservice_session08_lession02.service;


import org.example.javawebservice_session08_lession02.entity.Ticket;

public interface TicketService {

    Ticket bookTicket(String flightNumber, String passengerName);

    Ticket cancelTicket(Long ticketId);
}
