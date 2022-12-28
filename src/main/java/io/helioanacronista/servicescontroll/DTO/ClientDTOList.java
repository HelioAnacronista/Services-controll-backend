package io.helioanacronista.servicescontroll.DTO;

import io.helioanacronista.servicescontroll.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTOList {

    private Long id;
    private String name;

    public ClientDTOList(Client entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
