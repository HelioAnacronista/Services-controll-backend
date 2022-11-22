package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.WorksDTOS.WorkCreateDTO;
import io.helioanacronista.servicescontroll.DTO.WorksDTOS.WorkDTO;
import io.helioanacronista.servicescontroll.DTO.WorksDTOS.WorkMinDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.repositories.WorkRepository;
import io.helioanacronista.servicescontroll.services.exceptions.DataBaseNotFoundException;
import io.helioanacronista.servicescontroll.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class WorkServices {

    @Autowired
    private WorkRepository repository;

    @Autowired
    private ClientServices clientServices;

    @Autowired
    private CategoryServices categoryServices;


    public Work findById(Long id) {
        Work entity = repository.findById(id).orElseThrow(() -> new ExpressionException("Objeto não encontrado! Id: " + id));
        return entity;
    }

    public List<Work> findAll() {
        List<Work> workList = repository.findAll();
        return workList;
    }

    public WorkDTO insert(WorkDTO dto) {
        Work entity = new Work();
        copyDToToEntity(dto, entity);

        repository.save(entity);

        return new WorkDTO(entity);
    }

    public WorkCreateDTO update(Long id, WorkCreateDTO dto) {
        try {
            Work entity = repository.getReferenceById(id);
            copyDToToEntityCreate(dto, entity);
            entity = repository.save(entity);
            return new WorkCreateDTO(entity);
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

    public WorkMinDTO total() {
        WorkMinDTO workMinDTO = new WorkMinDTO();

        var listworks = findAll();
        workMinDTO.setServices(listworks);

        return workMinDTO;
    }


    //copiar dto para entidade
    private void copyDToToEntity (WorkDTO dto, Work entity) {
        entity.setId(null);
        entity.setTitulo(dto.getTitulo());
        entity.setDescricao(dto.getDescricao());
        entity.setDataAbertura(dto.getDataAbertura());
        entity.setDataFechamento(dto.getDataFechamento());

        Client findClient = clientServices.findById(dto.getClientsId());
        Category findCategory = categoryServices.findById(dto.getClientsId());

        entity.setClients(findClient);
        entity.setCategorys(findCategory);

        entity.setNomeCliente(dto.getNomeCliente());
        entity.setNomeCategoria(dto.getNomeCategoria());
        entity.setValor(dto.getValor());
    }

    //copiar dto para entidade
    private void copyDToToEntityCreate (WorkCreateDTO dto, Work entity) {
        entity.setTitulo(dto.getTitulo());
        entity.setDescricao(dto.getDescricao());
        entity.setDataAbertura(dto.getDataAbertura());
        entity.setDataFechamento(dto.getDataFechamento());

        Client findClient = clientServices.findById(dto.getClientsId());
        Category findCategory = categoryServices.findById(dto.getClientsId());

        entity.setClients(findClient);
        entity.setCategorys(findCategory);

        entity.setNomeCliente(dto.getNomeCliente());
        entity.setNomeCategoria(dto.getNomeCategoria());
        entity.setValor(dto.getValor());
    }

}
