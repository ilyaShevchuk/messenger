package ru.yandex.sbd.messenger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import ru.yandex.sbd.messenger.MessengerApplication;

/**
 * POJO equivalent for application.properties.
 * Include this class to your @Configuration using @EnableConfigurationProperties.
 *
 * @see MessengerApplication.ContextConfiguration
 */
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final ServerProperties server;

    @ConstructorBinding
    public AppProperties(ServerProperties server) {
        this.server = server;
    }

    public ServerProperties getServer() {
        return server;
    }

    public static class ServerProperties {
        private final int port;

        @ConstructorBinding
        public ServerProperties(int port) {
            this.port = port;
        }

        public int getPort() {
            return port;
        }
    }
}
