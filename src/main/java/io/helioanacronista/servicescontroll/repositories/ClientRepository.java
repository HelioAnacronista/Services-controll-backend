package io.helioanacronista.servicescontroll.repositories;

import io.helioanacronista.servicescontroll.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
