package cz.upce.nnpia.controllers;

import cz.upce.nnpia.dtos.request.LoginRequest;
import cz.upce.nnpia.dtos.response.JWTResponse;
import cz.upce.nnpia.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name="Authentication controller")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody @Valid final LoginRequest loginRequest){
        var result = authService.login(loginRequest);
        return ResponseEntity.ok(result);
    }
}
