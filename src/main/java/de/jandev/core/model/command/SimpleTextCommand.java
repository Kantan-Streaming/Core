package de.jandev.core.model.command;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@DiscriminatorValue("1")
public class SimpleTextCommand extends Command {

    @Column(nullable = false)
    private String text;
}
