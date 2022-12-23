package io.helioanacronista.servicescontroll.repositories;

import io.helioanacronista.servicescontroll.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByPhone(String phone);

    Page<Client> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
