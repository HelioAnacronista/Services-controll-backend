package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.CategoryDTO;
import io.helioanacronista.servicescontroll.DTO.CategoryDTOList;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryServices services;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO entity = services.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<CategoryDTOList>> getAllList() {
        List<CategoryDTOList> listDtos = services.getAllList();
        return ResponseEntity.ok(listDtos);
    }

    @GetMapping()
    public Page<CategoryDTO> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "name", required = false) String name
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        return services.findByNameContainingIgnoreCase(name, pageable);
    }



    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<CategoryDTO> create (@Valid @RequestBody CategoryDTO dto) {
        dto = services.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
        dto = services.update(id, dto);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping(value = "/{id}" ,produces="application/json")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        services.delete(id);
        return ResponseEntity.noContent().build();
    }
}
