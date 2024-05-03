package cz.upce.nnpia.dtos.response;
public record JWTResponse(
        UserResponse user,
        String token
) {
}
