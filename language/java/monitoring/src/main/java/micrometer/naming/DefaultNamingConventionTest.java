package micrometer.naming;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class DefaultNamingConventionTest {
    public static void main(String[] args) {
        // register prometheusRegistry
        PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        prometheusRegistry.config().namingConvention(NamingConvention.camelCase);
        Metrics.addRegistry(prometheusRegistry);

        MeterRegistry registry = Metrics.globalRegistry;

        // register naming convention
        registry.config().namingConvention();

        Counter counter = registry.counter("metric.count");
        counter.increment();

        // curl -X GET localhost:8080/prometheus
        // 당신은 metricCount_total 1.0 을 보게될 것 (camelCase)
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/prometheus", httpExchange -> {
                String response = prometheusRegistry.scrape();
                httpExchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            });

            new Thread(server::start).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
