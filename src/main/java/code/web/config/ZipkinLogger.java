package code.web.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/** Registra una advertencia concisa cuando el colector Zipkin no está disponible. */
@Slf4j
@Component
@ConditionalOnProperty(name = "management.tracing.export.zipkin.endpoint")
public class ZipkinLogger {
    private final URI endpoint;
    private final Duration connectTimeout;

    public ZipkinLogger(
            @Value("${management.tracing.export.zipkin.endpoint}") String endpoint,
            @Value("${management.tracing.export.zipkin.connect-timeout:1s}") Duration connectTimeout) {
        this.endpoint = URI.create(endpoint);
        this.connectTimeout = connectTimeout;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logAvailability() {
        try (Socket socket = new Socket()) {
            socket.connect(
                    new InetSocketAddress(endpoint.getHost(), port()),
                    Math.toIntExact(connectTimeout.toMillis()));
        } catch (IOException exception) {
            log.warn("El servicio Zipkin no está disponible en {}. Las trazas serán descartadas.", endpoint);
        }
    }

    private int port() {
        if (endpoint.getPort() != -1) {
            return endpoint.getPort();
        }
        return "https".equalsIgnoreCase(endpoint.getScheme()) ? 443 : 80;
    }
}
