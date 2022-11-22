package acktsap.websocket.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

// todo: https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-websocket 보고 정리
@SpringBootApplication
public class Application {

    @Configuration
    class WebConfig {

        @Bean
        public HandlerMapping handlerMapping() {
            Map<String, WebSocketHandler> map = new HashMap<>();
            map.put("/path", new MyWebSocketHandler());
            int order = -1; // before annotated controllers

            return new SimpleUrlHandlerMapping(map, order);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
