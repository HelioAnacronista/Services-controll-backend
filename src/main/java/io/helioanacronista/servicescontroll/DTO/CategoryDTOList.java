package io.helioanacronista.servicescontroll.DTO;

import io.helioanacronista.servicescontroll.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTOList {

    private Long id;
    private String name;

    public CategoryDTOList(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
