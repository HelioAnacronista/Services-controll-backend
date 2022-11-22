package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.categoriesDTOS.CategoryDTO;
import io.helioanacronista.servicescontroll.DTO.categoriesDTOS.CategoryMinDTO;
import io.helioanacronista.servicescontroll.DTO.categoriesDTOS.CategoryWithoutWorksDTO;
import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientDTO;
import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientMinDTO;
import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientWithoutWorksDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

    @Autowired
    private CategoryServices service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @GetMapping(value = "/categoryfull/{id}")
    public ResponseEntity<CategoryMinDTO> findByIdFull (@PathVariable Long id) {
        Category entity = service.findById(id);
        return ResponseEntity.ok().body(new CategoryMinDTO(entity));
    }


    @GetMapping(value = "/categoryfull")
    public ResponseEntity<List<CategoryMinDTO>> findAll() {
        List<Category> list = service.findAll();
        List<CategoryMinDTO> listDTO = list.stream().map(CategoryMinDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping
    public ResponseEntity<List<CategoryWithoutWorksDTO>> findAllCategoryFull() {
        List<CategoryWithoutWorksDTO> list = service.findAllCategoryFull();
        //List<ClientWithoutWorksDTO> listDTO = list.stream().map(ClientWithoutWorksDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create (@Valid @RequestBody CategoryDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryWithoutWorksDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryWithoutWorksDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
