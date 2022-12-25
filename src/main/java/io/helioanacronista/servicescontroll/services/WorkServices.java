package io.helioanacronista.servicescontroll.services;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;

import io.helioanacronista.servicescontroll.DTO.WorkCardDTO;
import io.helioanacronista.servicescontroll.DTO.WorkDTO;
import io.helioanacronista.servicescontroll.DTO.WorkMinDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Status;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.repositories.CategoryRepository;
import io.helioanacronista.servicescontroll.repositories.ClientRepository;
import io.helioanacronista.servicescontroll.repositories.ExpenseRepository;
import io.helioanacronista.servicescontroll.repositories.WorkRepository;
import io.helioanacronista.servicescontroll.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private ExpenseRepository expenseRepository;

    //return value-total e porcetagem dos servicos
    public WorkCardDTO getTotalValue() {
        //Puxa valores do banco de dados
        Double gastos = expenseRepository.getTotalValue();
        Double vendas = workRepository.getTotalValue();

        /*
        Se você quiser calcular a porcentagem de lucro,
        pode subtrair o valor total de gastos do valor total de vendas e dividir o resultado pelo valor total de vendas.
        A fórmula para calcular a porcentagem de lucro seria:
         */
        Double resultPorcentagem = ((vendas - gastos) / vendas) * 100;

        WorkCardDTO dto = new WorkCardDTO();
        dto.setValue(vendas);
        dto.setPercentage(resultPorcentagem);

        return dto;
    }

    //findID
    @Transactional(readOnly = true)
    public WorkDTO findbyId(Long id) {
        Work entity = workRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new WorkDTO(entity);

    }

    //getALL
    @Transactional(readOnly = true)
    public Page<Work> findAll(String name, Pageable pageable) {
        Page<Work> workList = workRepository.searchByName(name, pageable);
        return workList;
    }

    //getAllMin
    @Transactional(readOnly = true)
    public Page<WorkMinDTO> findMinAll(String name, Pageable pageable) {
        Page<Work> workList = workRepository.searchByName(name, pageable);
        return workList.map(x -> new WorkMinDTO(x));
    }


    //GetLast
    @Transactional(readOnly = true)
    public List<WorkDTO> findLastBy() {
        List<Work> result = workRepository.findTop8ByOrderByIdDesc();
        return result.stream().map(work -> new WorkDTO(work)).collect(Collectors.toList());
    }


    //CREATE
    @Transactional
    public WorkDTO insert(WorkDTO dto){
        Work entity = new Work();

        entity.setId(null);
        entity.setName(dto.getName());
        entity.setStatus(Status.toEnum(dto.getStatus()));
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
        entity.setName(dto.getName());
        entity.setStatus(Status.toEnum(dto.getStatus()));
        System.out.println(Status.toEnum(dto.getStatus()));
        //Encontrar o client e categoria pelo o ids
        Client client = clientRepository.findById(dto.getClient().getId()).orElseThrow( () -> new UsernameNotFoundException("CLIENT NOT FOUND" + dto.getClient().getId()));
        Category category = categoryRepository.findById(dto.getCategory().getId()).orElseThrow(() -> new UsernameNotFoundException("CATEGORIA NOT FOUD"+ dto.getCategory().getId()));

        entity.setCategory(category);
        entity.setClient(client);


        entity.setValor(dto.getValor());
    }

}
