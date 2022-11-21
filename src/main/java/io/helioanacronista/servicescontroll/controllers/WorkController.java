package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.WorkDTO;
import io.helioanacronista.servicescontroll.DTO.WorkMinDTO;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.services.WorkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/work")
public class WorkController {

    @Autowired
    private WorkServices service;

    @GetMapping(path = "/filter")
    public ResponseEntity<WorkMinDTO> total() {
        WorkMinDTO total = service.total();
        return ResponseEntity.ok().body(total);
    }

    @GetMapping
    public ResponseEntity<List<WorkDTO>> findAll() {
        List<Work> list = service.findAll();
        List<WorkDTO> listDTO = list.stream().map(obj -> new WorkDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Work> findById(@PathVariable Long id) {
        Work entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @PostMapping
    public ResponseEntity<WorkDTO> create (@RequestBody WorkDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }
}
