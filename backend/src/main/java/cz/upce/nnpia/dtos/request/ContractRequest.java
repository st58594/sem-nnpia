package cz.upce.nnpia.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record ContractRequest(
        @NotBlank(message = "The state must not be blank")
        String state,
        @NotEmpty(message = "The contract has to contain at least 1 product")
        Set<ContractProductRequest> contractProducts
) {
}
