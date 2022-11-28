package acktsap.spring.resttemplate.request;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class GetRequestTest {

    public static void main(String[] args) throws Exception {
        MockWebServer server = new MockWebServer();
        server.start();
        HttpUrl baseUrl = server.url("/test");

        RestTemplate restTemplate = new RestTemplate();

        server.enqueue(new MockResponse().addHeader("Content-Type", "text/plain").setBody("i am god"));
        ResponseEntity<String> getForEntityResponse = restTemplate.getForEntity(baseUrl.uri(), String.class);
        System.out.println("getForEntityResponse: " + getForEntityResponse.getBody());

        server.enqueue(new MockResponse().addHeader("Content-Type", "text/plain").setBody("i am god"));
        String getForStringResponse = restTemplate.getForObject(baseUrl.uri(), String.class);
        System.out.println("getForStringResponse: " + getForStringResponse);

        MockResponse mockResponse = new MockResponse()
            .addHeader("Content-Type", "text/plain")
            .setBody("i am god");
        server.enqueue(mockResponse);
        UriComponents url = UriComponentsBuilder.fromUri(baseUrl.uri())
            .queryParam("param1", "tt")
            .build();
        String urlResult = restTemplate.getForObject(url.toUri(), String.class);
        System.out.println("urlResult: " + urlResult);

        restTemplate.close();

        server.shutdown();
    }
}
