package io.helioanacronista.servicescontroll.services;


import io.helioanacronista.servicescontroll.DTO.WorkDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.repositories.CategoryRepository;
import io.helioanacronista.servicescontroll.repositories.ClientRepository;
import io.helioanacronista.servicescontroll.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WorkServices {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AccountingRepository accountingRepository;


    //findID
    public Work findbyId(Long id) {
        return workRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Id not foud"));
    }


    //CREATE
    public WorkDTO insert(WorkDTO dto){
        Work entity = new Work();

        entity.setId(null);
        entity.setNomeServico(dto.getNomeServico());
        //Encontrar o client e categoria pelo o ids
        Client client = clientRepository.findById(dto.getClient().getId()).orElseThrow( () -> new UsernameNotFoundException("CLIENT NOT FOUND" + dto.getClient().getId()));
        Category category = categoryRepository.findById(dto.getCategory().getId()).orElseThrow(() -> new UsernameNotFoundException("CATEGORIA NOT FOUD"+ dto.getCategory().getId()));
        Accounting accounting = accountingRepository.findById(dto.getAccounting().getId()).orElseThrow(() -> new UsernameNotFoundException("CATEGORIA NOT FOUD"+ dto.getAccounting().getId()));


        entity.setCategory(category);
        entity.setClient(client);
        entity.setAccounting(accounting);

        entity.setValor(dto.getValor());
        workRepository.save(entity);

        return new WorkDTO(entity);
    }


}
