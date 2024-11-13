package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository;

import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<AddressEntity,Long> {
}
