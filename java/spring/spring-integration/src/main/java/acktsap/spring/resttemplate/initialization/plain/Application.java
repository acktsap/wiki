package acktsap.spring.resttemplate.initialization.plain;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetAddress;

public class Application {

    public static void main(String[] args) throws Exception {
        MockWebServer server = new MockWebServer();
        server.start();
        HttpUrl baseUrl = server.url("/test");

        RestTemplate restTemplate = new RestTemplate();

        server.enqueue(new MockResponse().addHeader("Content-Type", "application/json").setBody("im test"));
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl.uri(), String.class);
        System.out.println("body: " + response.getBody());

        restTemplate.close();

        server.shutdown();
    }
}
