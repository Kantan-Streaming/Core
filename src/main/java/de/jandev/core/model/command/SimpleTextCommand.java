package de.jandev.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("1")
public class SimpleTextCommand extends Command {

    private String text;

}
