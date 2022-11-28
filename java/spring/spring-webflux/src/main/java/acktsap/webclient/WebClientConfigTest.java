package acktsap.webclient;

import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilderFactory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WebClientConfigTest {
    @SuppressWarnings("ConstantConditions")
    public static void main(String[] args) {
        // creation
        {
            WebClient webClient = WebClient.create("http://www.naver.com");
        }

        // mutate
        {
            WebClient webClient = WebClient.create("http://www.naver.com");
            WebClient another = webClient.mutate()
                .baseUrl("www.test.com")
                .build();
        }

        // builder
        {
            UriBuilderFactory uriBuilderFactory = null;
            WebClient webClient = WebClient.builder()
                .uriBuilderFactory(uriBuilderFactory) // provide custom uriBuilderFactory
                .defaultUriVariables(Map.of("uriKey", "uriValue")) // provide default uri variable (exclusive with uriBuilderFactory)
                .defaultHeader("testHeader", "headerValue") // provide custom header
                .defaultHeaders(headers -> headers.setAccept(List.of(MediaType.APPLICATION_JSON))) // profide custom headers
                .defaultCookie("defaultCookie", "cookieValue") // set default cookie
                .defaultRequest(spec -> spec.header("someHeader", "h~~")) // set default request spec
                .codecs(configurer -> {
                    configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024); // 2MB (default is 256KB)
                    configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder());
                    configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder());
                }) // set codecs
                .filter((request, next) -> {
                    ClientRequest anotherRequest = ClientRequest.from(request)
                        .header("uuid", UUID.randomUUID().toString())
                        .build();
                    return next.exchange(anotherRequest);
                }) // add filter at the end of filter chain
                .clientConnector(new ReactorClientHttpConnector()) // set client connector
                .build();
        }
    }
}
