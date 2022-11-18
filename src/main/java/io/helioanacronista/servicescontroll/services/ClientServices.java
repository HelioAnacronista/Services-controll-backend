package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.ClientDTO;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository repository;

    public Client findById(Long id) {
        Client entity = repository.findById(id).orElseThrow(() -> new ExpressionException("Objeto não encontrado! Id: " + id));
        return entity;
    }

    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        copyDTOToEntityUpdate(dto, entity);

        repository.save(entity);

        return new ClientDTO(entity);

    }

    private void copyDTOToEntityUpdate (ClientDTO dto, Client entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
    }
}
