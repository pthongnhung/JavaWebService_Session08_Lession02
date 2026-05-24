package org.example.javawebservice_session08_lession02.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTicketRequest {

    @NotBlank(message = "Flight number không được để trống")
    private String flightNumber;

    @NotBlank(message = "Passenger name không được để trống")
    private String passengerName;
}
