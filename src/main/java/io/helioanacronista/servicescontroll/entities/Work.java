package io.helioanacronista.servicescontroll.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "tb_services")
public class Work {

    @ApiModelProperty(value = "ID de serviços")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ApiModelProperty(value = "titulo de serviços")
    private String titulo;
    @ApiModelProperty(value = "descricao de serviços")
    private String descricao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    @ApiModelProperty(value = "Categoria de serviços referecial")
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category categorys;
    @ApiModelProperty(value = "Cliente de serviços referecial")
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client clients;

    @ApiModelProperty(value = "nome do cliente")
    private String nomeCliente;

    @ApiModelProperty(value = "nome da categoria")
    private String nomeCategoria;

    @ApiModelProperty(value = "valor do serviço")
    private Double valor;
}
