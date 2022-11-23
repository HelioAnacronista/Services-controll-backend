package io.helioanacronista.servicescontroll.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity()
@Table(name = "tb_clients")
public class Client {

    @ApiModelProperty(value = "ID do Cliente")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Nome do cliente")
    private String name;
    @ApiModelProperty(value = "Telefone do cliente")
    private String phone;
    @ApiModelProperty(value = "Endereço do cliente")
    private String address;

    @ApiModelProperty(value = "Lista de serviços do cliente")
    @OneToMany(mappedBy = "clients")
    @JsonIgnore
    private List<Work> services = new ArrayList<>();
}
