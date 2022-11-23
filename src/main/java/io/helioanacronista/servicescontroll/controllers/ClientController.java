package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientDTO;
import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientMinDTO;
import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientWithoutWorksDTO;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/client")
public class ClientController {

    @Autowired
    private ClientServices service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) {
        Client entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @GetMapping(value = "/clientfull/{id}")
    public ResponseEntity<ClientMinDTO> findByIdFull (@PathVariable Long id) {
        Client entity = service.findById(id);
        return ResponseEntity.ok().body(new ClientMinDTO(entity));
    }


    @GetMapping(value = "/clientfull")
    public ResponseEntity<List<ClientMinDTO>> findAll() {
        List<Client> list = service.findAll();
        List<ClientMinDTO> listDTO = list.stream().map(ClientMinDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClientWithoutWorksDTO>> findAllClientFull() {
        List<ClientWithoutWorksDTO> list = service.findAllClientFull();
        //List<ClientWithoutWorksDTO> listDTO = list.stream().map(ClientWithoutWorksDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ClientDTO> create (@Valid @RequestBody ClientDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientWithoutWorksDTO> update(@PathVariable Long id, @Valid @RequestBody ClientWithoutWorksDTO dto) throws Exception {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}