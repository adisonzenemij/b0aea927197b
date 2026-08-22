package code;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Engineering {

    private static final String ENV_FILE = ".env";

    public static void main(String[] args) {
        loadDotenv();
        SpringApplication.run(Engineering.class, args);
    }

    /**
     * Carga el archivo .env local y, al ejecutarse como WAR, el archivo incluido
     * en WEB-INF/classes. Las variables reales del sistema y los parámetros de la
     * JVM conservan prioridad sobre el contenido de .env.
     */
    static void loadDotenv() {
        Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

        for (DotenvEntry entry : dotenv.entries()) {
            setPropertyIfMissing(entry.getKey(), entry.getValue());
        }

        loadClasspathDotenv();
    }

    private static void loadClasspathDotenv() {
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
                    .forEach(entry -> setPropertyIfMissing(entry[0], entry[1]));
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

    private static void setPropertyIfMissing(String key, String value) {
        if (System.getenv(key) == null && System.getProperty(key) == null) {
            System.setProperty(key, value);
        }
    }
}
