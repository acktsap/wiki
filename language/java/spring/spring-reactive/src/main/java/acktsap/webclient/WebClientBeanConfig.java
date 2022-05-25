package acktsap.webclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebClientBeanConfig {

    static class TestClient {
        private final WebClient webClient;

        public TestClient(WebClient baseWebClient) {
            this.webClient = baseWebClient.mutate()
                .baseUrl("www.naver.com")
                .build();
        }

        @Override
        public String toString() {
            return "TestClient{" +
                "webClient=" + webClient +
                '}';
        }
    }

    @Bean
    public WebClient baseWebClient() {
        WebClient webClient = WebClient.builder()
            .defaultHeader("test", "tt")
            .build();
        System.out.println("register webclient: " + webClient);
        return webClient;
    }

    @Bean
    public TestClient testClient(WebClient baseWebClient) {
        TestClient testClient = new TestClient(baseWebClient);
        System.out.println("register testClient: " + testClient);

        return testClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebClientBeanConfig.class);
    }
}
