package io.helioanacronista.servicescontroll.DTO.WorksDTOS;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.helioanacronista.servicescontroll.entities.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDTO {


    private Long id;
    private String titulo;
    private String descricao;

    private LocalDate dataAbertura = LocalDate.now();
    private LocalDate dataFechamento;

    private Long categorysId;

    private Long clientsId;

    private String nomeCliente;
    private String nomeCategoria;

    private Double valor;

    @JsonIgnore
    private List<Work> servicesList = new ArrayList<>();

    public WorkDTO(Work entity) {
        id = entity.getId();
        titulo = entity.getTitulo();
        descricao = entity.getDescricao();
        dataAbertura = entity.getDataAbertura();
        dataFechamento = entity.getDataFechamento();

        categorysId = entity.getCategorys().getId();
        clientsId = entity.getClients().getId();

        nomeCategoria = entity.getCategorys().getName();
        nomeCliente = entity.getClients().getName();

        valor = entity.getValor();
    }
}
