package io.helioanacronista.servicescontroll.controllers;

import io.helioanacronista.servicescontroll.DTO.TotalCardDTO;
import io.helioanacronista.servicescontroll.services.AccountingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/accounting")
@RestController
@CrossOrigin(value = "*")
public class AccountingController {

    @Autowired
    private AccountingServices services;

    @GetMapping(value = "/total")
    public ResponseEntity<TotalCardDTO> findTotal() {
        TotalCardDTO dto = services.findTotal();;
        return ResponseEntity.ok().body(dto);
    }
}
