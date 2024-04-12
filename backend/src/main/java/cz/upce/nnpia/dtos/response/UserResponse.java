package cz.upce.nnpia.dtos.response;

import cz.upce.nnpia.model.Role;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        Set<Role> roles,
        LocalDateTime created,
        LocalDateTime updated) {
}
