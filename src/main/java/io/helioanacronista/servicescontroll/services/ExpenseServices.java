package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.ExpenseDTO;
import io.helioanacronista.servicescontroll.entities.Expense;
import io.helioanacronista.servicescontroll.repositories.ExpenseRepository;
import io.helioanacronista.servicescontroll.services.exceptions.DataBaseNotFoundException;
import io.helioanacronista.servicescontroll.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServices {

    @Autowired
    private ExpenseRepository repository;

    public ExpenseDTO findById(Long id) {
        Expense entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado! Id: " + id));
        return new ExpenseDTO(entity);
    }

    public List<ExpenseDTO> findAll() {
        List<Expense> expenseList = repository.findAll();
       return expenseList.stream().map(ExpenseDTO::new).collect(Collectors.toList());
    }


    public ExpenseDTO insert(ExpenseDTO dto) {
        Expense entity = new Expense();
        if (dto.getId() != null) {
            dto.setId(null);
        }

        //valid
        validTest(dto);

        convertToEntity(dto, entity);
        repository.save(entity);
        return new ExpenseDTO(entity);
    }

    public ExpenseDTO update(Long id, ExpenseDTO dto) {
        try {
            Expense entity = repository.getReferenceById(id);
            convertToEntity(dto, entity);
            entity = repository.save(entity);
            return new ExpenseDTO(entity);
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

    //copy dto to entity
    private void convertToEntity (ExpenseDTO dto, Expense entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setValor(dto.getValor());
    }


    //Valid category
    public void validTest(ExpenseDTO dto) {
        Optional<Expense> entity = repository.findByName(dto.getName());
        //String phone = entity.get().getPhone();
        if (entity.isPresent() && entity.get().getId() != dto.getId()) {
            throw new ResourceNotFoundException("Expense já cadastrado no sistema!");
        }
    }
}
