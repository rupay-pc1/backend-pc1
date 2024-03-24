package com.pc1.backendrupay.repositories;

import com.pc1.backendrupay.domain.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketModel, UUID> {
}
