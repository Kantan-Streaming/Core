package de.jandev.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("2")
public class ActionCommand extends Command {

    @Enumerated(EnumType.STRING)
    private ActionType actionType;
    private String reply;

}
