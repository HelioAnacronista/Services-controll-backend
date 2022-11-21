package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.CategoryDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

@Service
public class CategoryServices {

    @Autowired
    private CategoryRepository repository;

    public Category findById(Long id) {
        Category entity = repository.findById(id).orElseThrow(() -> new ExpressionException("Objeto não encontrado! Id: " + id));
        return entity;
    }

    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        copyDTOToEntityUpdate(dto, entity);

        repository.save(entity);

        return new CategoryDTO(entity);

    }

    private void copyDTOToEntityUpdate (CategoryDTO dto, Category entity) {
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
    }
}
