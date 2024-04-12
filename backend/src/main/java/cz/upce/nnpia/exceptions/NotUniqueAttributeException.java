package cz.upce.nnpia.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotUniqueAttributeException extends RuntimeException{
    public NotUniqueAttributeException(String message) {
        super(message);
    }
}
