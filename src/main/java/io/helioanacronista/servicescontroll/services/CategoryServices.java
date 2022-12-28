package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.CategoryDTO;
import io.helioanacronista.servicescontroll.DTO.CategoryDTOList;
import io.helioanacronista.servicescontroll.DTO.ClientDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.repositories.CategoryRepository;
import io.helioanacronista.servicescontroll.services.exceptions.DataBaseNotFoundException;
import io.helioanacronista.servicescontroll.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServices {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado! Id: " + id));
        return new CategoryDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<CategoryDTOList> getAllList() {
        List<Category> result = repository.findAll();
        return result.stream().map(x -> new CategoryDTOList(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        Page<Category> result = repository.findByNameContainingIgnoreCase(name, pageable);
        return result.map(x -> new CategoryDTO(x));
    }

    @Transactional
    public CategoryDTO insert( CategoryDTO dto) {
        Category entity = new Category();
        if (dto.getId() != null) {
            dto.setId(null);
        }

        //valid
//        validCategory(dto);

        convertToEntity(dto, entity);
        repository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.getReferenceById(id);
            convertToEntity(dto, entity);
            entity = repository.save(entity);
            return new CategoryDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
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
    private void convertToEntity (CategoryDTO dto, Category entity) {
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
