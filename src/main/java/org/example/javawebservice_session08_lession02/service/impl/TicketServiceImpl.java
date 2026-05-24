package org.example.javawebservice_session08_lession02.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.javawebservice_session08_lession02.entity.Flight;
import org.example.javawebservice_session08_lession02.entity.Ticket;
import org.example.javawebservice_session08_lession02.repository.FlightRepository;
import org.example.javawebservice_session08_lession02.repository.TicketRepository;
import org.example.javawebservice_session08_lession02.service.TicketService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    @Override
    public Ticket bookTicket(String flightNumber, String passengerName) {

        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay"));

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("Chuyến bay đã hết vé");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Ticket ticket = Ticket.builder()
                .flightId(flight.getId())
                .passengerName(passengerName)
                .status("BOOKED")
                .build();

        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket cancelTicket(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé"));

        ticket.setStatus("CANCELED");

        return ticketRepository.save(ticket);
    }
}
