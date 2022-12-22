package io.helioanacronista.servicescontroll.controllers;


import io.helioanacronista.servicescontroll.DTO.WorkDTO;
import io.helioanacronista.servicescontroll.DTO.WorkMinDTO;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.services.WorkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/work")
public class WorkController {

    @Autowired
    private WorkServices workServices;

    @GetMapping("/totalvalue")
    public ResponseEntity<Double> getTotalValue() {
        Double totalValue = workServices.getTotalValue();
        return ResponseEntity.ok().body(totalValue);
    }

    @GetMapping(value = "/{id}",produces ="application/json")
    public ResponseEntity<WorkDTO> findById(@PathVariable Long id) {
        WorkDTO dto = workServices.findbyId(id);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<Work>> findAll (@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {
        Page<Work> listDtos = workServices.findAll(name, pageable);
        return ResponseEntity.ok(listDtos);
    }

    @PostMapping(produces ="application/json", consumes="application/json")
    public ResponseEntity<WorkDTO> create (@RequestBody WorkDTO dto) {
        WorkDTO en = workServices.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(en);
    }
//
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<WorkDTO> update(@PathVariable Long id, @Valid @RequestBody WorkDTO dto) {
//        dto = workServices.update(id, dto);
//        return ResponseEntity.ok(dto);
//    }
//
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        workServices.(id);
//        return ResponseEntity.noContent().build();
//    }
}
