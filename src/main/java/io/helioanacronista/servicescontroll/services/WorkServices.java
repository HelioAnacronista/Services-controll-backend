package io.helioanacronista.servicescontroll.services;


import io.helioanacronista.servicescontroll.DTO.ExpenseDTO;
import io.helioanacronista.servicescontroll.DTO.WorkDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Expense;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.repositories.CategoryRepository;
import io.helioanacronista.servicescontroll.repositories.ClientRepository;
import io.helioanacronista.servicescontroll.repositories.WorkRepository;
import io.helioanacronista.servicescontroll.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkServices {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    //return value-total dos servicos
    public Double getTotalValue() {
        return workRepository.getTotalValue();
    }

    //findID
    public WorkDTO findbyId(Long id) {
        Work entity = workRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new WorkDTO(entity);

    }

    //getALL
    public List<WorkDTO> findAll() {
        List<Work> workList = workRepository.findAll();
        return workList.stream().map(WorkDTO::new).collect(Collectors.toList());
    }

    //CREATE
    public WorkDTO insert(WorkDTO dto){
        Work entity = new Work();

        entity.setId(null);
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
        //Encontrar o client e categoria pelo o ids
        Client client = clientRepository.findById(dto.getClient().getId())
                .orElseThrow( () -> new UsernameNotFoundException("CLIENT NOT FOUND" + dto.getClient().getId()));
        Category category = categoryRepository.findById(dto.getCategory().getId())
                .orElseThrow(() -> new UsernameNotFoundException("CATEGORIA NOT FOUD"+ dto.getCategory().getId()));
        entity.setCategory(category);
        entity.setClient(client);


        entity.setValor(dto.getValor());

        workRepository.save(entity);

        return new WorkDTO(entity);
    }

    //copy dto to entity
    private void convertToEntity (WorkDTO dto, Work entity) {
        entity.setId(null);
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
        //Encontrar o client e categoria pelo o ids
        Client client = clientRepository.findById(dto.getClient().getId()).orElseThrow( () -> new UsernameNotFoundException("CLIENT NOT FOUND" + dto.getClient().getId()));
        Category category = categoryRepository.findById(dto.getCategory().getId()).orElseThrow(() -> new UsernameNotFoundException("CATEGORIA NOT FOUD"+ dto.getCategory().getId()));

        entity.setCategory(category);
        entity.setClient(client);


        entity.setValor(dto.getValor());
    }

}
