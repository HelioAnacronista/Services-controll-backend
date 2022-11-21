package io.helioanacronista.servicescontroll.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    private String descricao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category categorys;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client clients;

    private String nomeCliente;
    private String nomeCategoria;

    private Double valor;
}
