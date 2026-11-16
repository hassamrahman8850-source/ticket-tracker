package com.hassam.tickets.ticket;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Ticket create(CreateTicketRequest request) {
        Ticket ticket = new Ticket(request.title(), request.description());
        return repository.save(ticket);
    }

    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Ticket findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    @Transactional
    public Ticket update(Long id, UpdateTicketRequest request) {
        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
        ticket.update(request.title(), request.description(), request.status());
        return ticket;
    }

    @Transactional
    public void delete(Long id) {
        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
        repository.delete(ticket);
    }
}