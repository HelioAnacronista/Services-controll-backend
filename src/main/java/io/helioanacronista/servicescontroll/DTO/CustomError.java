package io.helioanacronista.servicescontroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class CustomError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

}