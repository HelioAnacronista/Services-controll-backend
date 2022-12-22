package io.helioanacronista.servicescontroll.DTO;

import io.helioanacronista.servicescontroll.entities.Work;

public class WorkMinDTO {

    private Long id;
    private String nome;
    private Double valor;
    private Integer status;

    public WorkMinDTO(Work entity) {
        id = entity.getId();
        nome = entity.getNome();
        status = entity.getStatus();
        valor = entity.getValor();
    }
}
