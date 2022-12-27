package io.helioanacronista.servicescontroll.controllers;


import io.helioanacronista.servicescontroll.DTO.WorkCardDTO;
import io.helioanacronista.servicescontroll.DTO.WorkDTO;
import io.helioanacronista.servicescontroll.DTO.WorkMinDTO;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.services.WorkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/work")
public class WorkController {

    @Autowired
    private WorkServices workServices;

    @GetMapping("/totalvalue")
    public ResponseEntity<WorkCardDTO> getTotalValue() {
        WorkCardDTO totalValue = workServices.getTotalValue();
        return ResponseEntity.ok().body(totalValue);
    }

    @GetMapping(value = "/{id}",produces ="application/json")
    public ResponseEntity<WorkDTO> findById(@PathVariable Long id) {
        WorkDTO dto = workServices.findbyId(id);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<Work>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest
                .of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        Page<Work> listDtos = workServices.findAll(pageable);
        return ResponseEntity.ok(listDtos);
    }

    @GetMapping(value = "/min")
    public ResponseEntity<Page<WorkMinDTO>> findMinAll (
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest
                .of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        Page<WorkMinDTO> listDtos = workServices.findMinAll(pageable);
        return ResponseEntity.ok(listDtos);
    }

    @GetMapping(value = "/lasts")
    public ResponseEntity<List<WorkDTO>> findMinAll () {
        List<WorkDTO> listDtos = workServices.findLastBy();
        return ResponseEntity.ok(listDtos);
    }

    @PostMapping(produces ="application/json", consumes="application/json")
    public ResponseEntity<WorkDTO> create (@RequestBody WorkDTO dto) {
        WorkDTO en = workServices.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(en);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<WorkDTO> update(@PathVariable Long id, @Valid @RequestBody WorkDTO dto) {
        dto = workServices.update(id, dto);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
