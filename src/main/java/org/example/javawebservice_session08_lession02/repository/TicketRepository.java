package org.example.javawebservice_session08_lession02.repository;


import org.example.javawebservice_session08_lession02.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}