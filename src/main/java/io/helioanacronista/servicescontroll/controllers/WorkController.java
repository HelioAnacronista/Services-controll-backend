package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.WorksDTOS.WorkCreateDTO;
import io.helioanacronista.servicescontroll.DTO.WorksDTOS.WorkDTO;
import io.helioanacronista.servicescontroll.DTO.WorksDTOS.WorkMinDTO;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.services.WorkServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/work")
public class WorkController {

    @Autowired
    private WorkServices service;

    @ApiOperation(value = "Retorna um serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um serviço"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = ""),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @GetMapping(value = "/{id}",produces ="application/json")
    public ResponseEntity<Work> findById(@PathVariable Long id) {
        Work entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @ApiOperation(value = "Retorna uma lista serviços COM valor total e COM dados cliente e categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista serviços completa"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @GetMapping(path = "/filter", produces ="application/json")
    public ResponseEntity<WorkMinDTO> total() {
        WorkMinDTO total = service.total();
        return ResponseEntity.ok().body(total);
    }

    @ApiOperation(value = "Retorna uma lista de serviços")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista serviços completa"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @GetMapping(produces ="application/json")
    public ResponseEntity<List<WorkDTO>> findAll() {
        List<Work> list = service.findAll();
        List<WorkDTO> listDTO = list.stream().map(obj -> new WorkDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @ApiOperation(value = "Cria um serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Categoria criada com sucesso"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = "id referecial não encontrado"),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(produces ="application/json", consumes="application/json")
    public ResponseEntity<WorkDTO> create (@RequestBody WorkDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @ApiOperation(value = "Alteração em um servico existente COM seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Alteração feita com sucesso"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = "id referecial não encontrado"),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", produces ="application/json", consumes="application/json")
    public ResponseEntity<WorkCreateDTO> update(@PathVariable Long id, @RequestBody WorkCreateDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Deleta um serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleta com sucesso"),
            @ApiResponse(code = 400, message = "Esse objeto esta refereciado em outra entidade / Falha de integridade refencial"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
            @ApiResponse(code = 401, message = "Não está autenticado"),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}", produces ="application/json")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
