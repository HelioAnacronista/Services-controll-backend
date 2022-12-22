package io.helioanacronista.servicescontroll.repositories;

import io.helioanacronista.servicescontroll.DTO.WorkDTO;
import io.helioanacronista.servicescontroll.DTO.WorkMinDTO;
import io.helioanacronista.servicescontroll.entities.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;


@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    @Query(value = "SELECT SUM(w.valor) FROM Work w")
    Double getTotalValue();

    @Query("SELECT w FROM Work w " +
            "WHERE UPPER(w.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Work> searchByName(String name, Pageable pageable);
}
