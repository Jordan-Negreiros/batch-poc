package com.github.jordannegreiros.batchpoc;

import com.github.jordannegreiros.batchpoc.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
