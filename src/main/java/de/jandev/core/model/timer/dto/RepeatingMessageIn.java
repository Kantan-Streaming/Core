package de.jandev.core.model.timer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RepeatingMessageIn {

    @NotNull
    private Boolean active;

    @NotNull
    private Integer delay;

    @NotNull
    @Size(min = 1, max = 500)
    private String message;
}
