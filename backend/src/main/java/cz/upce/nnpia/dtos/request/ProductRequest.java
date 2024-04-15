package cz.upce.nnpia.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank String name,
        @NotNull Double price,
        @NotNull Integer inStock) {
}
