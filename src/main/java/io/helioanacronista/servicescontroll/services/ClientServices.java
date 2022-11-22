package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientDTO;
import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientWithoutWorksDTO;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.repositories.ClientRepository;
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
public class ClientServices {

    @Autowired
    private ClientRepository repository;

    public Client findById(Long id) {
        Client entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado! Id: " + id));
        return entity;
    }


    public List<Client> findAll() {
        List<Client> clientList = repository.findAll();
        return clientList;
    }

    public List<ClientWithoutWorksDTO> findAllClientFull() {
        List<Client> result = repository.findAll();

        return result.stream().map(x -> new ClientWithoutWorksDTO(x)).toList();
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

    public ClientWithoutWorksDTO update(@PathVariable Long id, ClientWithoutWorksDTO dto){
        try {
            Client entity = repository.getReferenceById(id);
            copyDTOToEntityUpdateWithoutWorks(dto, entity);
            entity = repository.save(entity);
            return new ClientWithoutWorksDTO(entity);
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

    //Copy entity to dto ClientDTO
    private void copyDTOToEntityUpdate (ClientDTO dto, Client entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
    }

    //Copy entity to dto ClientWithoutWorksDTO
    private void copyDTOToEntityUpdateWithoutWorks (ClientWithoutWorksDTO dto, Client entity) {
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
