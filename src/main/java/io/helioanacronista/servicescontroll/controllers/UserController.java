package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.ClientDTO;
import io.helioanacronista.servicescontroll.DTO.UserDTO;
import io.helioanacronista.servicescontroll.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getById (@PathVariable Long id) {
        UserDTO dto = service.getById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/me", produces ="application/json")
    public ResponseEntity<UserDTO> getMe() {
        UserDTO dto = service.getMe();
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update (@PathVariable Long id, @RequestBody UserDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }
}
