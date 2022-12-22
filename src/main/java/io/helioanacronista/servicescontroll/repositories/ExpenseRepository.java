package io.helioanacronista.servicescontroll.repositories;

import io.helioanacronista.servicescontroll.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByName(String nome);
}
