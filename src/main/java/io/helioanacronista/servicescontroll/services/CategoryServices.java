package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.categoriesDTOS.CategoryDTO;
import io.helioanacronista.servicescontroll.DTO.categoriesDTOS.CategoryWithoutWorksDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.repositories.CategoryRepository;
import io.helioanacronista.servicescontroll.services.exceptions.DataBaseNotFoundException;
import io.helioanacronista.servicescontroll.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServices {

    @Autowired
    private CategoryRepository repository;

    public Category findById(Long id) {
        Category entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado! Id: " + id));
        return entity;
    }

    public List<Category> findAll() {
        List<Category> categoryList = repository.findAll();
        return categoryList;
    }

    public List<CategoryWithoutWorksDTO> findAllCategoryFull() {
        List<Category> result = repository.findAll();

        return result.stream().map(x -> new CategoryWithoutWorksDTO(x)).toList();
    }

    public CategoryDTO insert( CategoryDTO dto) {
        Category entity = new Category();
        if (dto.getId() != null) {
            dto.setId(null);
        }

        //valid
        validCategory(dto);

        copyDTOToEntity(dto, entity);
        repository.save(entity);
        return new CategoryDTO(entity);
    }

    public CategoryWithoutWorksDTO update(@PathVariable Long id, CategoryWithoutWorksDTO dto){
        try {
            Category entity = repository.getReferenceById(id);
            copyDTOToEntityUpdateWithoutWorks(dto, entity);
            entity = repository.save(entity);
            return new CategoryWithoutWorksDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recuso não encontrado");
        }
    }

    public void delete(Long id){
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Recuso não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseNotFoundException("Falha de integridade refencial");
        }
    }

    //Copy entity to dto CategoryDTO
    private void copyDTOToEntity (CategoryDTO dto, Category entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }

    //Copy entity to dto CategoryWithoutWorksDTO
    private void copyDTOToEntityUpdateWithoutWorks (CategoryWithoutWorksDTO dto, Category entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }

    //Valid category
    public void validCategory(CategoryDTO dto) {
        Optional<Category> entity = repository.findByName(dto.getName());
        //String phone = entity.get().getPhone();
        if (entity.isPresent() && entity.get().getId() != dto.getId()) {
            throw new ResourceNotFoundException("Categoria já cadastrado no sistema!");
        }
    }
}
