package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientDTO;
import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientMinDTO;
import io.helioanacronista.servicescontroll.DTO.clientsDTOS.ClientWithoutWorksDTO;
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
import java.util.stream.Collectors;


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

    @ApiOperation(value = "Retorna um cliente COM lista de serviços e COM o seu valor total")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma categoria"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
    })
    @GetMapping(value = "/clientfull/{id}",produces="application/json")
    public ResponseEntity<ClientMinDTO> findByIdFull (@PathVariable Long id) {
        Client entity = service.findById(id);
        return ResponseEntity.ok().body(new ClientMinDTO(entity));
    }

    @ApiOperation(value = "Retorna uma lista de cliente COM lista de serviços e COM o seus valores total")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista categorias completa"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
    })
    @GetMapping(value = "/clientfull",produces="application/json")
    public ResponseEntity<List<ClientMinDTO>> findAllfull() {
        List<Client> list = service.findAll();
        List<ClientMinDTO> listDTO = list.stream().map(ClientMinDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @ApiOperation(value = "Retorna uma lista de cliente SEM lista de serviços e SEM o seus valores total")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista categorias completa"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<List<ClientWithoutWorksDTO>> findAll() {
        List<ClientWithoutWorksDTO> list = service.findAllClientFull();
        //List<ClientWithoutWorksDTO> listDTO = list.stream().map(ClientWithoutWorksDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(list);
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

    @ApiOperation(value = "Alteração em um cliente existente COM seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Alteração feita com sucesso"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes="application/json",produces="application/json")
    public ResponseEntity<ClientWithoutWorksDTO> update(@PathVariable Long id, @Valid @RequestBody ClientWithoutWorksDTO dto) throws Exception {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Deleta um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleta com sucesso"),
            @ApiResponse(code = 400, message = "Esse objeto esta refereciado em outra entidade / Falha de integridade refencial"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}",produces="application/json")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}