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

@Entity(name = "tb_categories")
public class Category {

    @ApiModelProperty(value = "ID da categoria")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(value = "Nome/titulo da categoria")
    private String name;

    @ApiModelProperty(value = "Descriçao da categoria")
    private String description;

    @OneToMany(mappedBy = "categorys")
    @JsonIgnore
    @ApiModelProperty(value = "Lista de servicos por categoria")
    private List<Work> services = new ArrayList<>();
}
