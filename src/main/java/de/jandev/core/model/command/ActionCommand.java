package de.jandev.core.model.command;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@DiscriminatorValue("2")
public class ActionCommand extends Command {

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String reply;
}
