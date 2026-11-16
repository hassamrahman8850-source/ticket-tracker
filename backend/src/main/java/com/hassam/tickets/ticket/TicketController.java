package com.hassam.tickets.ticket;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse create(@Valid @RequestBody CreateTicketRequest request) {
        return TicketResponse.from(service.create(request));
    }

    @GetMapping
    public List<TicketResponse> list() {
        return service.findAll().stream()
                .map(TicketResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public TicketResponse get(@PathVariable Long id) {
        return TicketResponse.from(service.findById(id));
    }

    @PutMapping("/{id}")
    public TicketResponse update(@PathVariable Long id,
                                 @Valid @RequestBody UpdateTicketRequest request) {
        return TicketResponse.from(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}