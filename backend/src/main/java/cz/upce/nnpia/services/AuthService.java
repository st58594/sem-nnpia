package cz.upce.nnpia.services;

import cz.upce.nnpia.dtos.request.LoginRequest;
import cz.upce.nnpia.dtos.response.JWTResponse;
import cz.upce.nnpia.exceptions.InvalidCredentialException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public JWTResponse login(final LoginRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            var user = userService.loadUserByUsername(request.password());
            var jwtToken = jwtService.generateToken(user);

            return new JWTResponse(jwtToken);

        } catch (AuthenticationException e){
            throw new InvalidCredentialException("Invalid username or password");
        }
    }
}
