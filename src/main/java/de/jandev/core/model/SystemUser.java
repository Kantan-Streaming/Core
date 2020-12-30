package de.jandev.core.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SystemUser {

    @Id
    private String id;
    private String accessToken;
    private String refreshToken;
}
