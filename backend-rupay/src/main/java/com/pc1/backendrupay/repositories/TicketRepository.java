package com.pc1.backendrupay.repositories;

import com.pc1.backendrupay.domain.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketModel, UUID> {

    @Query("SELECT t FROM TicketModel t WHERE t.id = :id")
    List<TicketModel> findByUserId(UUID id);
}
