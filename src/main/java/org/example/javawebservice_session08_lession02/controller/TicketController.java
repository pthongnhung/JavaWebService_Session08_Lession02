package org.example.javawebservice_session08_lession02.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.javawebservice_session08_lession02.dto.ApiResponse;
import org.example.javawebservice_session08_lession02.dto.BookTicketRequest;
import org.example.javawebservice_session08_lession02.entity.Ticket;
import org.example.javawebservice_session08_lession02.service.TicketService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/book")
    public ApiResponse<Ticket> bookTicket(
            @Valid @RequestBody BookTicketRequest request
    ) {

        Ticket ticket = ticketService.bookTicket(
                request.getFlightNumber(),
                request.getPassengerName()
        );

        return new ApiResponse<>(
                true,
                "Đặt vé thành công",
                ticket
        );
    }

    @PostMapping("/cancel/{ticketId}")
    public ApiResponse<Ticket> cancelTicket(
            @PathVariable Long ticketId
    ) {

        Ticket ticket = ticketService.cancelTicket(ticketId);

        return new ApiResponse<>(
                true,
                "Hủy vé thành công",
                ticket
        );
    }
}
