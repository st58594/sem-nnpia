package cz.upce.nnpia.dtos.response;

public record ProductResponse(
        Long id,
        String name,
        double price,
        int inStock) {
}
