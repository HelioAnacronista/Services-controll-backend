package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.CategoryDTO;
import io.helioanacronista.servicescontroll.DTO.ClientDTO;
import io.helioanacronista.servicescontroll.DTO.ClientDTOList;
import io.helioanacronista.servicescontroll.DTO.ExpenseDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Expense;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.repositories.ClientRepository;
import io.helioanacronista.servicescontroll.repositories.WorkRepository;
import io.helioanacronista.servicescontroll.services.exceptions.DataBaseNotFoundException;
import io.helioanacronista.servicescontroll.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private WorkRepository workRepository;

    public ClientDTO findById(Long id) {
        Client entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado! Id: " + id));
        return new ClientDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ClientDTOList> getAllList() {
        List<Client> result = repository.findAll();
        return result.stream().map(x -> new ClientDTOList(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        Page<Client> result = repository.findByNameContainingIgnoreCase(name, pageable);
        return result.map(x -> new ClientDTO(x));
    }

    public ClientDTO insert( ClientDTO dto) {
        Client entity = new Client();
        if (dto.getId() != null) {
            dto.setId(null);
        }
        //valid
//        validTest(dto);

        convertToEntity(dto, entity);
        repository.save(entity);
        return new ClientDTO(entity);
    }

    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getReferenceById(id);
            convertToEntity(dto, entity);
            entity = repository.save(entity);
            return new ClientDTO(entity);
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
    private void convertToEntity (ClientDTO dto, Client entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
    }

    //Valid Client
    public void validTest(ClientDTO dto) {
        Optional<Client> entity = repository.findByPhone(dto.getPhone());
        //String phone = entity.get().getPhone();
        if (entity.isPresent() && entity.get().getId() != dto.getId()) {
            throw new ResourceNotFoundException("Cliente já cadastrado no sistema!");
        }
    }

}
