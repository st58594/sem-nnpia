package cz.upce.nnpia.dtos.response;

import cz.upce.nnpia.model.State;

import java.time.LocalDateTime;
import java.util.Set;

public record ContractResponse(
        Long id,
        State state,
        Double totalPrice,
        UserResponse user,
        Set<ContractProductResponse> contractProducts,
        LocalDateTime created,
        LocalDateTime updated) {
}
