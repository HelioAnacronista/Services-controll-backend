package io.helioanacronista.servicescontroll.DTO;


import io.helioanacronista.servicescontroll.entities.Status;
import io.helioanacronista.servicescontroll.entities.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer status;

    private CategoryDTO category;
    private ClientDTO client;

    private Double valor;

    public WorkDTO(Work entity) {
        id = entity.getId();
        name = entity.getName();
        status = entity.getStatus().getCodigo();
        category = new CategoryDTO(entity.getCategory());
        client = new ClientDTO(entity.getClient());
        valor = entity.getValor();
    }
}
