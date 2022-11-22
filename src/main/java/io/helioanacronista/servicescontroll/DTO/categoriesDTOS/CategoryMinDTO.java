package io.helioanacronista.servicescontroll.DTO.categoriesDTOS;

import io.helioanacronista.servicescontroll.DTO.WorksDTOS.WorkDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.entities.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMinDTO {

    private Long id;
    private String name;
    private String description;

    private List<WorkDTO> services = new ArrayList<>();

    public CategoryMinDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        for (Work works : entity.getServices()) {
            services.add(new WorkDTO(works));
        }
    }

    public Double getTotal() {
        Double sum = 0.0;

        for (WorkDTO works : services) {
            sum += works.getValor();
        }

        return sum;
    }
}
