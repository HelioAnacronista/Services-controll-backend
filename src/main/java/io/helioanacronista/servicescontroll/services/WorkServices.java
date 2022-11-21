package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.WorkDTO;
import io.helioanacronista.servicescontroll.DTO.WorkMinDTO;
import io.helioanacronista.servicescontroll.entities.Category;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Work;
import io.helioanacronista.servicescontroll.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkServices {

    @Autowired
    private WorkRepository repository;

    @Autowired
    private ClientServices clientServices;

    @Autowired
    private CategoryServices categoryServices;

    public List<Work> findAll() {
        List<Work> workList = repository.findAll();
        return workList;
    }

    public WorkMinDTO total() {
        WorkMinDTO workMinDTO = new WorkMinDTO();

        var listworks = findAll();
        workMinDTO.setServices(listworks);

        return workMinDTO;
    }

    public Work findById(Long id) {
        Work entity = repository.findById(id).orElseThrow(() -> new ExpressionException("Objeto não encontrado! Id: " + id));
        return entity;
    }

    public WorkDTO insert(WorkDTO dto) {
        Work entity = new Work();
        copyDToToEntity(dto, entity);

        repository.save(entity);

        return new WorkDTO(entity);
    }

    private void copyDToToEntity (WorkDTO dto, Work entity) {
        entity.setId(null);
        entity.setTitulo(dto.getTitulo());
        entity.setDescricao(dto.getDescricao());
        entity.setDataAbertura(dto.getDataAbertura());
        entity.setDataFechamento(dto.getDataFechamento());

        Client findClient = clientServices.findById(dto.getClientsId());
        Category findCategory = categoryServices.findById(dto.getClientsId());

        entity.setClients(findClient);
        entity.setCategorys(findCategory);

        entity.setNomeCliente(dto.getNomeCliente());
        entity.setNomeCategoria(dto.getNomeCategoria());
        entity.setValor(dto.getValor());
    }

}
