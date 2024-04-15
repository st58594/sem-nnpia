package cz.upce.nnpia.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UserRequest(
        @NotBlank(message = "Username must not be blank")
        String username,
        @NotBlank(message = "Email must not be blank")
        String email,
        @NotBlank(message = "FirstName must not be blank")
        String firstName,
        @NotBlank(message = "LastName must not be blank")
        String lastName,
        @NotBlank(message = "Password must not be blank")
        String password,
        @NotEmpty(message = "Roles must not be empty")
        Set<RoleRequest> roles) {
}
