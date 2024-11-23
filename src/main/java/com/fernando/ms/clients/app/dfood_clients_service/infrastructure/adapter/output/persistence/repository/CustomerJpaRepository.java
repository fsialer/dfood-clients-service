package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository;

import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity,Long> {
    boolean existsByEmailIgnoreCase(String email);
}
