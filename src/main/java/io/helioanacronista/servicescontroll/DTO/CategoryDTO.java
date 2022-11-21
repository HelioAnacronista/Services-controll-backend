package io.helioanacronista.servicescontroll.DTO;

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
public class CategoryDTO {

    private Long id;
    private String nome;
    private String descricao;

    private List<WorkDTO> services = new ArrayList<>();

    public CategoryDTO(Category entity) {
        id = entity.getId();
        nome = entity.getNome();
        descricao = entity.getDescricao();
        for (Work works : entity.getServices()) {
            services.add(new WorkDTO(works));
        }
    }
}
