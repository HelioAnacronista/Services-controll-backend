package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.UserDTO;
import io.helioanacronista.servicescontroll.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @ApiOperation(value = "Retorna o perfil que está logado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retornado com sucesso"),
            @ApiResponse(code = 404, message = "Objeto não encontrado / Foi gerada uma exceção"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/me", produces ="application/json")
    public ResponseEntity<UserDTO> getMe() {
        UserDTO dto = service.getMe();
        return ResponseEntity.ok(dto);
    }

}
