package acktsap.spring.resttemplate.converter;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class ConverterTest {

    static class TestData {

        private int intValue;

        private String stringValue;

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public void setStringValue(String stringValue) {
            this.stringValue = stringValue;
        }

        public String toString() {
            return "TestData{" +
                "intValue=" + intValue +
                ", stringValue='" + stringValue + '\'' +
                '}';
        }
    }

    public static void main(String[] args) throws Exception {
        MockWebServer server = new MockWebServer();
        server.start();
        HttpUrl baseUrl = server.url("/test");

        RestTemplate restTemplate = new RestTemplate(getHttpMessageConverters());

        server.enqueue(new MockResponse().addHeader("Content-Type", "text/plain").setBody("""
            {
                "intValue": 3,
                "stringValue": "wow",
                "extraValue": "extra"
            }"""));
        ResponseEntity<TestData> response = restTemplate.getForEntity(baseUrl.uri(), TestData.class);
        System.out.println("body: " + response.getBody());

        restTemplate.close();

        server.shutdown();
    }

    private static List<HttpMessageConverter<?>> getHttpMessageConverters() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(List.of(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        return List.of(
            mappingJackson2HttpMessageConverter,
            stringHttpMessageConverter
        );
    }
}
