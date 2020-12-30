package de.jandev.core.eventhandler;

import com.github.twitch4j.pubsub.events.ChannelSubscribeEvent;
import de.jandev.core.service.TwitchAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwitchSubscriptionEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchSubscriptionEventHandler.class);

    public TwitchSubscriptionEventHandler(TwitchAPIService twitchAPIService) {
        twitchAPIService.getSimpleEventHandler().onEvent(ChannelSubscribeEvent.class, this::onSubEvent);
    }

    private void onSubEvent(ChannelSubscribeEvent event) {
        // TODO: Send a chat message and notify the browser source about the subscribe
    }


}
