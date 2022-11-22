package io.helioanacronista.servicescontroll.DTO.WorksDTOS;

import io.helioanacronista.servicescontroll.entities.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkMinDTO {

    private List<Work> services = new ArrayList<>();

    public Double getTotal() {
        Double sum = 0.0;

        for (Work works : services) {
            sum += works.getValor();
        }

        return sum;
    }
}
