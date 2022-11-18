package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.ClientDTO;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


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

    @PostMapping
    public ResponseEntity<ClientDTO> create ( @RequestBody ClientDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }
}