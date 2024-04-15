package cz.upce.nnpia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum State {
    CREATED("Created"),
    PRODUCTION("Production"),
    DONE("Done"),
    CANCELLED("Cancelled");

    private final String name;

}