package io.helioanacronista.servicescontroll.DTO;

import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;

    private String name;
    private String phone;
    private String address;

    private List<WorkDTO> services = new ArrayList<>();

    public ClientDTO(Client entity) {
        id = entity.getId();
        name = entity.getName();
        phone = entity.getPhone();
        address = entity.getAddress();
        for (Work works : entity.getServices()) {
            services.add(new WorkDTO(works));
        }

    }
}
