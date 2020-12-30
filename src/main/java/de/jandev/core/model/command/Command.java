package de.jandev.core.model.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.jandev.core.model.user.User;
import lombok.Data;

import javax.persistence.*;

@Inheritance
@Data
@Entity
@DiscriminatorColumn(name = "command_type",
        discriminatorType = DiscriminatorType.INTEGER)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private User user;
    private Character prefix;
    private String name;

    public String getFullName() {
        return prefix + name;
    }

}
