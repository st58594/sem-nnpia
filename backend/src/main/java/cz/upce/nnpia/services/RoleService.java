package cz.upce.nnpia.services;

import cz.upce.nnpia.model.Role;
import cz.upce.nnpia.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    public Role findByRole(String role) throws NoSuchElementException {
        return roleRepository.findByRole(role).orElseThrow(() -> new NoSuchElementException(role + "not found"));
    }
}
