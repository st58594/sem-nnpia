package cz.upce.nnpia.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ContractProductRequest(
        @NotNull(message = "The product id must not be null")
        Long productID,
        @NotNull(message = "The ordered product must not be null")
        @Min(value = 1, message = "The ordered min value is 1")
        @Max(value = Integer.MAX_VALUE, message = "The ordered max value is " + Integer.MAX_VALUE)
        Integer ordered) {
}
