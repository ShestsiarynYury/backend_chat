package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import app.controller.SocketMessageHandler;

@Configuration
@EnableWebSocket
public class MyWebSocketConfiguration implements WebSocketConfigurer {
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new SocketMessageHandler(), "/chat").setAllowedOrigins("*");
	}

	@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(10000000);
        container.setMaxBinaryMessageBufferSize(10000000);
        container.setMaxSessionIdleTimeout(100000000l);
        return container;
    }
}
