package code;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

@SpringBootApplication
public class Engineering {

    private static final String ENV_FILE = ".env";
    private static final String DOTENV_PROPERTY_SOURCE = "applicationDotenv";

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Engineering.class);
        application.addInitializers(dotenvInitializer());
        application.run(args);
    }

    /**
     * Carga el archivo .env local y, al ejecutarse como WAR, el archivo incluido
     * en WEB-INF/classes como propiedades propias de esta aplicación.
     *
     * <p>No se modifican propiedades globales de la JVM: WildFly puede alojar
     * varios WAR y cada uno debe conservar su propia configuración.
     */
    static ApplicationContextInitializer<ConfigurableApplicationContext> dotenvInitializer() {
        Map<String, Object> properties = dotenvProperties();

        return context -> context
            .getEnvironment()
            .getPropertySources()
            .addFirst(new MapPropertySource(DOTENV_PROPERTY_SOURCE, properties));
    }

    private static Map<String, Object> dotenvProperties() {
        Map<String, Object> properties = new LinkedHashMap<>();
        Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

        for (DotenvEntry entry : dotenv.entries()) {
            properties.put(entry.getKey(), entry.getValue());
        }

        loadClasspathDotenv(properties);
        return Map.copyOf(properties);
    }

    private static void loadClasspathDotenv(Map<String, Object> properties) {
        try (InputStream input = Engineering.class
            .getClassLoader()
            .getResourceAsStream(ENV_FILE)) {
            if (input == null) {
                return;
            }

            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8)
            )) {
                reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .filter(line -> !line.startsWith("#"))
                    .map(Engineering::dotenvEntry)
                    .filter(entry -> entry.length == 2)
                    .forEach(entry -> properties.put(entry[0], entry[1]));
            }
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo cargar el .env empaquetado", exception);
        }
    }

    private static String[] dotenvEntry(String line) {
        String normalized = line.startsWith("export ")
            ? line.substring(7).trim()
            : line;
        int separator = normalized.indexOf('=');

        if (separator <= 0) {
            return new String[0];
        }

        String key = normalized.substring(0, separator).trim();
        String value = normalized.substring(separator + 1).trim();
        return new String[] {key, unquote(value)};
    }

    private static String unquote(String value) {
        if (value.length() < 2) {
            return value;
        }

        char first = value.charAt(0);
        char last = value.charAt(value.length() - 1);
        if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
            return value.substring(1, value.length() - 1);
        }

        return value;
    }
}
