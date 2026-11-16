package com.hassam.tickets.ticket;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(Long id) {
        super("Ticket " + id + " not found");
    }
}