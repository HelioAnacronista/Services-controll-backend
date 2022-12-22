package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.ClientDTO;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.services.ClientServices;
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
@RequestMapping(value = "/api/client")
public class ClientController {

    @Autowired
    private ClientServices service;

    @ApiOperation(value = "Retorna um cliente sem lista de serviços")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma categoria"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
    })
    @GetMapping(value = "/{id}",produces="application/json")
    public ResponseEntity<Client> findById(@PathVariable Long id) {
        Client entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }


    @ApiOperation(value = "Cria um cliente COM uma lista vazia de serviços")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Categoria criada com sucesso"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<ClientDTO> create (@Valid @RequestBody ClientDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }




}