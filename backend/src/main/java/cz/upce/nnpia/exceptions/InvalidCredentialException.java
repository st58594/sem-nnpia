package cz.upce.nnpia.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException(String message) {
        super(message);
    }
}
