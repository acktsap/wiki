package acktsap.webclient;

import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientRequest;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.function.Function;

public class WebClientNettyConnectorTest {
    public static void main(String[] args) {
        // with custom connection pool setting
        {
            // see https://projectreactor.io/docs/netty/release/reference/index.html#_connection_pool_2
            ConnectionProvider connectionProvider = ConnectionProvider.builder("custom")
                .maxConnections(50) // default: # of core * 2
                .maxIdleTime(Duration.ofSeconds(20)) /// 20초동안 안쓰이면 죽여
                .maxLifeTime(Duration.ofSeconds(60)) // 예를들어 load balancer때문에 ip가 막 바뀔때 lifetime을 오래가져가면 안됨. 이럴때 사용.
                .pendingAcquireTimeout(Duration.ofSeconds(60)) // connection request가 60초 동안 connection 못받으면 죽어
                .pendingAcquireMaxCount(3) // connection request는 최대 3개까지 기다릴 수 있음
                .evictInBackground(Duration.ofSeconds(120)) // 2분마다 제거 대상인 connection 제거
                .build();
            HttpClient httpClient = HttpClient.create(connectionProvider);

            WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
        }

        // resource non-global setting
        {
            // 기본적으로 global reactor.netty.http.HttpResources를 공통으로 쓰는데 (recommanded) 이걸 global하게 사용하지 않으려면 이렇게..
            ReactorResourceFactory factory = new ReactorResourceFactory();
            factory.setUseGlobalResources(false); // no global!!

            WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(factory, Function.identity()))
                .build();
        }

        // timeout setting
        {
            HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // connection 자체 timeout, default: 30s
                .option(ChannelOption.SO_KEEPALIVE, true) // enable keep alive ping, default: false
                .responseTimeout(Duration.ofSeconds(2)); // 모든 요청에 대한 timeout

            WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

            webClient.get()
                .uri("www.naver.com")
                .httpRequest(clientHttpRequest -> {
                    HttpClientRequest reactorRequest = clientHttpRequest.getNativeRequest();
                    reactorRequest.responseTimeout(Duration.ofSeconds(3)); // 한 요청에 대한 timeout
                })
                .retrieve()
                .bodyToMono(String.class);
        }
    }
}
