package io.helioanacronista.servicescontroll.DTO.categoriesDTOS;

import io.helioanacronista.servicescontroll.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithoutWorksDTO {

    private Long id;
    @Size(min = 3, max = 80, message = "O nome precisa ter entre 3 a 8 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;
    @NotBlank(message = "Campo requerido")
    private String description;

    public CategoryWithoutWorksDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
    }

}

