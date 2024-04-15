package cz.upce.nnpia.dtos.response;

public record ContractProductResponse(
        ProductResponse product,
        int ordered) {
}
