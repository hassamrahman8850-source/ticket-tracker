package com.hassam.tickets.ticket;

import java.time.Instant;

/**
 * The JSON we send back. Keeping this separate from the Ticket entity means
 * the database shape and the API shape can change independently.
 */
public record TicketResponse(
        Long id,
        String title,
        String description,
        TicketStatus status,
        Instant createdAt) {

    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedAt());
    }
}
