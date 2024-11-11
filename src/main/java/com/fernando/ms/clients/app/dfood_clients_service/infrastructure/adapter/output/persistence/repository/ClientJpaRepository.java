package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository;

import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJpaRepository extends JpaRepository<ClientEntity,Long> {
    boolean existsByEmailIgnoreCase(String email);
}
