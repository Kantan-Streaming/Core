package de.jandev.core.utility;

import com.github.twitch4j.TwitchClient;
import de.jandev.core.model.timer.RepeatingMessage;

public class RepeatingMessageTask implements Runnable {

    private final RepeatingMessage repeatingMessage;
    private final TwitchClient twitchClient;

    public RepeatingMessageTask(RepeatingMessage repeatingMessage, TwitchClient twitchClient) {
        this.repeatingMessage = repeatingMessage;
        this.twitchClient = twitchClient;
    }

    @Override
    public void run() {
        twitchClient.getChat().sendMessage(repeatingMessage.getUser().getUsername(), repeatingMessage.getMessage());
    }
}