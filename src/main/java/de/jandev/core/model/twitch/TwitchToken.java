package de.jandev.core.model.twitch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TwitchToken {

    private String accessToken;
    private String refreshToken;
}
