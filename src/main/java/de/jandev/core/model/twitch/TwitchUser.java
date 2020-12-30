package de.jandev.core.model.twitch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TwitchUser {

    private String display_name;
    private String _id;
    private String name;
    private String type;
    private String bio;
    private String created_at;
    private String updated_at;
    private String logo;
    private String email;
    private boolean email_verified;
    private boolean partnered;
    private boolean twitter_connected;
    // Notifications -> push and email not relevant

}
