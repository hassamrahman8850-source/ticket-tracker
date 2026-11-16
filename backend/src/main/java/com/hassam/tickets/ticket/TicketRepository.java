package com.hassam.tickets.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * No implementation needed: Spring Data generates it at startup.
 * JpaRepository already gives us save(), findAll(), findById(), deleteById()...
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByOrderByCreatedAtDesc();
}
