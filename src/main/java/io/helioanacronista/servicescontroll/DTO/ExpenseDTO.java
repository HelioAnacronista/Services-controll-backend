package io.helioanacronista.servicescontroll.DTO;

import io.helioanacronista.servicescontroll.entities.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {

    private Long id;
    private String name;
    private Double valor;

    public ExpenseDTO(Expense entity) {
        id = entity.getId();
        name = entity.getName();
        valor = entity.getValor();
    }
}
