package de.jandev.core.model.command.dto;

import de.jandev.core.model.command.ActionType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ActionCommandIn {

    @NotNull
    @Size(min = 1, max = 1)
    private String prefix;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private ActionType actionType;

    @Size(min = 1, max = 500)
    private String reply;
}
