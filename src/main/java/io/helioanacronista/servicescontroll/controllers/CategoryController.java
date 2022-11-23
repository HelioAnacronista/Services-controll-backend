package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.categoriesDTOS.CategoryDTO;
import io.helioanacronista.servicescontroll.DTO.categoriesDTOS.CategoryMinDTO;
import io.helioanacronista.servicescontroll.DTO.categoriesDTOS.CategoryWithoutWorksDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.services.CategoryServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping(value = "/api/category")
public class CategoryController {

    @Autowired
    private CategoryServices service;

    @ApiOperation(value = "Retorna uma categoria SEM lista de serviços")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma categoria"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @GetMapping(value = "/{id}",produces="application/json")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @ApiOperation(value = "Retorna uma categoria COM lista de serviços e COM o seu valor total")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma categoria"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @GetMapping(value = "/categoryfull/{id}",produces="application/json")
    public ResponseEntity<CategoryMinDTO> findByIdFull (@PathVariable Long id) {
        Category entity = service.findById(id);
        return ResponseEntity.ok().body(new CategoryMinDTO(entity));
    }

    @ApiOperation(value = "Retorna uma lista categorias COM lista de serviços e COM o seus valores total")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista categorias completa"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @GetMapping(value = "/categoryfull",produces="application/json")
    public ResponseEntity<List<CategoryMinDTO>> findAllFull() {
        List<Category> list = service.findAll();
        List<CategoryMinDTO> listDTO = list.stream().map(CategoryMinDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @ApiOperation(value = "Retorna uma lista categorias SEM lista de serviços e SEM o seus valores total")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista categorias completa"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<List<CategoryWithoutWorksDTO>> findAll() {
        List<CategoryWithoutWorksDTO> list = service.findAllCategoryFull();
        //List<ClientWithoutWorksDTO> listDTO = list.stream().map(ClientWithoutWorksDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @ApiOperation(value = "Cria uma categorias COM uma lista vazia de serviços")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Categoria criada com sucesso"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<CategoryDTO> create (@Valid @RequestBody CategoryDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @ApiOperation(value = "Alteração em uma categorias existente COM seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Alteração feita com sucesso"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", produces="application/json",consumes="application/json")
    public ResponseEntity<CategoryWithoutWorksDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryWithoutWorksDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Deleta uma categorias")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleta com sucesso"),
            @ApiResponse(code = 400, message = "Esse objeto esta refereciado em outra entidade / Falha de integridade refencial"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}" ,produces="application/json")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
