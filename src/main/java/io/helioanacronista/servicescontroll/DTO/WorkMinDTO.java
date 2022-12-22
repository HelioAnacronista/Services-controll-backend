package io.helioanacronista.servicescontroll.DTO;

import io.helioanacronista.servicescontroll.entities.Status;
import io.helioanacronista.servicescontroll.entities.Work;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkMinDTO {

    private Long id;
    private String name;
    private Double valor;
    private Status status;

    public WorkMinDTO(Work entity) {
        id = entity.getId();
        name = entity.getName();
        status = Status.toEnum(entity.getStatus().getCodigo());
        valor = entity.getValor();
    }
}
