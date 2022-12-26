package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.TotalCardDTO;
import io.helioanacronista.servicescontroll.repositories.ExpenseRepository;
import io.helioanacronista.servicescontroll.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountingServices {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private ExpenseRepository expenseRepository;


    public TotalCardDTO findTotal() {
        Double gastos = expenseRepository.getTotalValue();
        Double vendas = workRepository.getTotalValue();


        Double resultPorcentagem = ((vendas - gastos) / vendas) * 100;
        Double resultPorcentagemGastos = (gastos / vendas) * 100;

        TotalCardDTO dto = new TotalCardDTO();
        dto.setValue(vendas - gastos);
        dto.setPercentage(resultPorcentagem - resultPorcentagemGastos);
        return dto;
    }
}
