package io.helioanacronista.servicescontroll.DTO;

import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    private List<ServiceDTO> services = new ArrayList<>();

    public ClientDTO(Client entity) {
        id = entity.getId();
        name = entity.getName();
        phone = entity.getPhone();
        address = entity.getAddress();
        for (Service rol : entity.getServices()) {
            services.add(new ServiceDTO(rol));
        }

    }
}
