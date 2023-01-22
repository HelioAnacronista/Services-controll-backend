package io.helioanacronista.servicescontroll.services;

import io.helioanacronista.servicescontroll.DTO.ClientDTO;
import io.helioanacronista.servicescontroll.DTO.UserDTO;
import io.helioanacronista.servicescontroll.entities.Client;
import io.helioanacronista.servicescontroll.entities.Role;
import io.helioanacronista.servicescontroll.entities.User;
import io.helioanacronista.servicescontroll.repositories.UserRepository;
import io.helioanacronista.servicescontroll.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        return user;
    }

    public User authenticated() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return repository.findByEmail(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Email not found");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getMe() {
        User entitiy = authenticated();
        return new UserDTO(entitiy);
    }

    @Transactional(readOnly = true)
    public UserDTO getById(Long id) {
        User entity = repository.getReferenceById(id);
        return new UserDTO(entity);
    }

    public UserDTO update(Long id, UserDTO dto) {
        try {
            User entity = repository.getReferenceById(id);
            convertToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    //copy dto to entity
    private void convertToEntity (UserDTO dto, User entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
    }
}