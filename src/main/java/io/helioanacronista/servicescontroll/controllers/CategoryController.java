package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.CategoryDTO;
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
