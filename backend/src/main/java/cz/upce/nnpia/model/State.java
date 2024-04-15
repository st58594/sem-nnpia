package cz.upce.nnpia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum State {
    CREATED("Vytvořeno"),
    PRODUCTION("V realizaci"),
    DONE("Dokončeno"),
    CANCELLED("Zrušeno");

    private final String name;

}