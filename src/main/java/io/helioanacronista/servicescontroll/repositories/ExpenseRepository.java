package io.helioanacronista.servicescontroll.repositories;

import io.helioanacronista.servicescontroll.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByName(String nome);

    @Query(value = "SELECT SUM(e.valor) FROM Expense e")
    Double getTotalValue();

    Page<Expense> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
