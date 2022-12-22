package io.helioanacronista.servicescontroll.controllers;


import io.helioanacronista.servicescontroll.DTO.WorkDTO;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.services.WorkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/work")
public class WorkController {

    @Autowired
    private WorkServices workServices;

    @GetMapping(value = "/{id}",produces ="application/json")
    public ResponseEntity<Work> findById(@PathVariable Long id) {
        Work entity = workServices.findbyId(id);

        return ResponseEntity.ok().body(entity);
    }

    @PostMapping(produces ="application/json", consumes="application/json")
    public ResponseEntity<WorkDTO> create (@RequestBody WorkDTO dto) {
        WorkDTO en = workServices.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(en);
    }
}
