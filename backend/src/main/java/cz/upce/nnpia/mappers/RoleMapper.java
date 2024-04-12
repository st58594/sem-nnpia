package cz.upce.nnpia.mappers;

import cz.upce.nnpia.dtos.request.RoleRequest;
import cz.upce.nnpia.model.Role;
import cz.upce.nnpia.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleMapper {
    private final RoleService roleService;

    public Set<Role> toRole(Set<RoleRequest> roleRequestSet){
        return roleRequestSet
                .stream()
                .map(roleRequest -> roleService.findByRole(roleRequest.role()))
                .collect(Collectors.toSet());
    }
}
