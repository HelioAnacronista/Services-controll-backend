package io.helioanacronista.servicescontroll.repositories;

import io.helioanacronista.servicescontroll.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    @Query(value = "SELECT SUM(w.valor) FROM Work w")
    Double getTotalValue();
}
