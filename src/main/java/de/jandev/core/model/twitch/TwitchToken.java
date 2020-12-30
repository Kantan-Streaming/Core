package de.jandev.core.model.twitch;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TwitchToken {

    private String accessToken;
    private String refreshToken;
}
