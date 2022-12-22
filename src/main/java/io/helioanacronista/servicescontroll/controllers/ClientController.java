package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.ClientDTO;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    private ClientServices service;


    @GetMapping(value = "/{id}",produces="application/json")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        ClientDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
    List<ClientDTO> dtoList = service.findAll();
    return ResponseEntity.ok(dtoList);
    }


    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<ClientDTO> create (@Valid @RequestBody ClientDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }




}