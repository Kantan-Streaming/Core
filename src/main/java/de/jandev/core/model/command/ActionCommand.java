package de.jandev.core.model.command;

import lombok.*;

import javax.persistence.*;

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

    @Column(nullable = false)
    private String reply;
}
