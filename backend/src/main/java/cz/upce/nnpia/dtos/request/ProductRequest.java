package cz.upce.nnpia.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank(message = "The product name must not be blank")
        String name,
        @NotNull(message = "The product price must not be null")
        Double price,
        @NotNull(message = "The product inStock must not be null")
        Integer inStock) {
}
