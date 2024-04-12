package cz.upce.nnpia.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
        @NotBlank String role) {
}
