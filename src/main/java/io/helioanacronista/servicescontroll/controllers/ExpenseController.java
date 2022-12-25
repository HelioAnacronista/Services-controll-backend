package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.ExpenseCardDTO;
import io.helioanacronista.servicescontroll.DTO.ExpenseDTO;
import io.helioanacronista.servicescontroll.DTO.WorkCardDTO;
import io.helioanacronista.servicescontroll.services.ExpenseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/expense")
public class ExpenseController {

    @Autowired
    private ExpenseServices service;

    @GetMapping("/totalvalue")
    public ResponseEntity<ExpenseCardDTO> getTotalValue() {
        ExpenseCardDTO totalValue = service.getTotalValue();
        return ResponseEntity.ok().body(totalValue);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpenseDTO> findById(@PathVariable Long id) {
        ExpenseDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> findAll() {
        List<ExpenseDTO> listDtos = service.findAll();
        return ResponseEntity.ok(listDtos);
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> insert(@Valid @RequestBody ExpenseDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ExpenseDTO> update(@PathVariable Long id, @Valid @RequestBody ExpenseDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
