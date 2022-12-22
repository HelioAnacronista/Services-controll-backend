package io.helioanacronista.servicescontroll.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

}
