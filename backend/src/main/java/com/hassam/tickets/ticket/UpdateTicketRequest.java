package com.hassam.tickets.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * The JSON body for updating a ticket. Same rules as creating,
 * plus a status (OPEN, IN_PROGRESS or CLOSED).
 */
public record UpdateTicketRequest(
        @NotBlank(message = "title is required")
        @Size(max = 255, message = "title must be 255 characters or fewer")
        String title,

        @Size(max = 2000, message = "description must be 2000 characters or fewer")
        String description,

        @NotNull(message = "status is required")
        TicketStatus status) {
}