package io.helioanacronista.servicescontroll.DTO.categoriesDTOS;

import io.helioanacronista.servicescontroll.DTO.WorksDTOS.WorkDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.entities.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    @Size(min = 3, max = 80, message = "O nome precisa ter entre 3 a 8 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;
    @NotBlank(message = "Campo requerido")
    private String description;

    private List<WorkDTO> services = new ArrayList<>();

    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        for (Work works : entity.getServices()) {
            services.add(new WorkDTO(works));
        }
    }
}
