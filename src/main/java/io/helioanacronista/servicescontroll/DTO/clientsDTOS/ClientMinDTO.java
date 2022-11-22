package io.helioanacronista.servicescontroll.DTO.clientsDTOS;

import io.helioanacronista.servicescontroll.DTO.WorksDTOS.WorkDTO;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientMinDTO {

    private Long id;

    @Size(min = 3, max = 80, message = "O nome precisa ter entre 3 a 8 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;
    @NotBlank(message = "Campo requerido")
    private String phone;
    @NotBlank(message = "Campo requerido")
    private String address;

    private List<WorkDTO> services = new ArrayList<>();

    public ClientMinDTO(Client entity) {
        id = entity.getId();
        name = entity.getName();
        phone = entity.getPhone();
        address = entity.getAddress();
        for (Work works : entity.getServices()) {
            services.add(new WorkDTO(works));
        }

    }

    public Double getTotal() {
        Double sum = 0.0;

        for (WorkDTO works : services) {
            sum += works.getValor();
        }

        return sum;
    }
}
