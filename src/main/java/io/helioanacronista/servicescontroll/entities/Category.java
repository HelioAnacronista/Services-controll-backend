package io.helioanacronista.servicescontroll.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "tb_category")
public class Category {

    @ApiModelProperty(value = "ID da categoria")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Nome/titulo da categoria")
    private String name;

    @ApiModelProperty(value = "Descriçao da categoria")
    private String description;

}
