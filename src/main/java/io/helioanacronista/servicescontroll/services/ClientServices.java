package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.ClientDTO;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.repositories.ClientRepository;
import io.helioanacronista.servicescontroll.repositories.WorkRepository;
import io.helioanacronista.servicescontroll.services.exceptions.DataBaseNotFoundException;
import io.helioanacronista.servicescontroll.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private WorkRepository workRepository;

    public Client findById(Long id) {
        Client entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado! Id: " + id));
        return entity;
    }

    public List<Client> findAll() {
        List<Client> clientList = repository.findAll();
        return clientList;
    }

    public ClientDTO insert( ClientDTO dto) {
        Client entity = new Client();
        if (dto.getId() != null) {
            dto.setId(null);
        }
        //valid
        validClient(dto);
        copyDTOToEntityUpdate(dto, entity);
        repository.save(entity);
        return new ClientDTO(entity);
    }


    //Copy entity to dto ClientDTO
    private void copyDTOToEntityUpdate (ClientDTO dto, Client entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
    }

    //Valid Client
    public void validClient(ClientDTO dto) {
        Optional<Client> entity = repository.findByPhone(dto.getPhone());
        //String phone = entity.get().getPhone();
        if (entity.isPresent() && entity.get().getId() != dto.getId()) {
            throw new ResourceNotFoundException("Cliente já cadastrado no sistema!");
        }
    }

}
