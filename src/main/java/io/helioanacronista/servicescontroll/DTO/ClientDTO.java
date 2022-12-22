package io.helioanacronista.servicescontroll.DTO;

import io.helioanacronista.servicescontroll.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;

    @Size(min = 3, max = 80, message = "O nome precisa ter entre 3 a 8 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;
    @NotBlank(message = "Campo requerido")
    private String phone;
    @NotBlank(message = "Campo requerido")
    private String address;

    public ClientDTO(Client entity) {
        id = entity.getId();
        name = entity.getName();
        phone = entity.getPhone();
        address = entity.getAddress();
    }
}
