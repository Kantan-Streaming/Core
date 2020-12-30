package de.jandev.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("de.jandev.core.repository")
public class CoreApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
        LOGGER.info("\n    __ __            __              ____        __     _____________    __   ___    ____  ____\n" +
                "   / //_/___ _____  / /_____ _____  / __ )____  / /_   /_  __/_  __/ |  / /  /   |  / __ \\/  _/\n" +
                "  / ,< / __ `/ __ \\/ __/ __ `/ __ \\/ __  / __ \\/ __/    / /   / /  | | / /  / /| | / /_/ // /  \n" +
                " / /| / /_/ / / / / /_/ /_/ / / / / /_/ / /_/ / /_     / /   / /   | |/ /  / ___ |/ ____// /   \n" +
                "/_/ |_\\__,_/_/ /_/\\__/\\__,_/_/ /_/_____/\\____/\\__/    /_/   /_/    |___/  /_/  |_/_/   /___/   \n" +
                "                                                                                               \n");
    }
}
