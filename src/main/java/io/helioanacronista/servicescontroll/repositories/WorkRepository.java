package io.helioanacronista.servicescontroll.repositories;

import io.helioanacronista.servicescontroll.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

}
