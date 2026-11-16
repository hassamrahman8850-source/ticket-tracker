package com.hassam.tickets.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(
        @NotBlank(message = "title is required")
        @Size(max = 255, message = "title must be 255 characters or fewer")
        String title,

        @Size(max = 2000, message = "description must be 2000 characters or fewer")
        String description) {
}