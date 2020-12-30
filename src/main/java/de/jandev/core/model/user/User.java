package de.jandev.core.model.user;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    private String id;
    private String username;
    private boolean active;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

}
