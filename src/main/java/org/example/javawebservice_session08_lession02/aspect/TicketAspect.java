package org.example.javawebservice_session08_lession02.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.javawebservice_session08_lession02.entity.ErrorLog;
import org.example.javawebservice_session08_lession02.entity.Flight;
import org.example.javawebservice_session08_lession02.entity.Ticket;
import org.example.javawebservice_session08_lession02.repository.ErrorLogRepository;
import org.example.javawebservice_session08_lession02.repository.FlightRepository;
import org.example.javawebservice_session08_lession02.repository.TicketRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
//
//@Aspect
//@Component
//@RequiredArgsConstructor
//import java.time.Duration;
//import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class TicketAspect {

    private final ErrorLogRepository errorLogRepository;
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;



    @Around("execution(* org.example.javawebservice_session08_lession02.service.impl.TicketServiceImpl.bookTicket(..))")
    public Object sanitizePassengerName(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        String flightNumber = (String) args[0];
        String passengerName = (String) args[1];

        passengerName = passengerName.trim().replaceAll("\\s+", " ").toUpperCase();

        args[1] = passengerName;

        System.out.println("Tên sau khi chuẩn hóa: " + passengerName);

        return joinPoint.proceed(args);
    }

    /*
        ============================
        @Before - Business Rule
        ============================
     */

    @Before("execution(* org.example.javawebservice_session08_lession02.service.impl.TicketServiceImpl.cancelTicket(..))")
    public void validateCancelTicket(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();

        Long ticketId = (Long) args[0];

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé"));

        Flight flight = flightRepository.findById(ticket.getFlightId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay"));

        long hours = Duration.between(
                LocalDateTime.now(),
                flight.getDepartureTime()
        ).toHours();

        if (hours < 24) {
            throw new RuntimeException("Không thể hủy vé trước giờ bay dưới 24h");
        }
    }

    /*
        ============================
        @AfterThrowing - Logging
        ============================
     */

    @AfterThrowing(
            pointcut = "execution(* org.example.javawebservice_session08_lession02.service..*(..))",
            throwing = "ex"
    )
    public void logError(JoinPoint joinPoint, Exception ex) {

        ErrorLog log = ErrorLog.builder()
                .timestamp(LocalDateTime.now())
                .methodName(joinPoint.getSignature().getName())
                .exceptionMessage(ex.getMessage())
                .build();

        errorLogRepository.save(log);

        System.out.println("Đã lưu log lỗi vào database");
    }
}