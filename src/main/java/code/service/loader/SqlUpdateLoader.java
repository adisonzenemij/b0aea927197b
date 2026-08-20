package code.service.loader;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

/** Actualiza los valores predeterminados de MySQL después de insertarlos. */
@Service
@Profile("!test")
@DependsOn("sqlInsertLoader")
@RequiredArgsConstructor
public class SqlUpdateLoader {
    private static final String MYSQL_URL_PREFIX = "jdbc:mysql:";
    private static final String OPERATION = "database/mysql/update";

    private final JdbcTemplate jdbcTemplate;
    private final Environment environment;

    @PostConstruct
    public void loadSqlFiles() {
        String databaseUrl = environment.getProperty("spring.datasource.url");
        if (databaseUrl == null || !databaseUrl.startsWith(MYSQL_URL_PREFIX)) {
            return;
        }

        updateDefaultData();
    }

    private void updateDefaultData() {
        ClassPathResource[] resources = {
                new ClassPathResource(OPERATION + "/role_data.sql"),
                new ClassPathResource(OPERATION + "/user_data.sql"),
                new ClassPathResource(OPERATION + "/image_ext.sql")
        };

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(resources);
        populator.execute(jdbcTemplate.getDataSource());
    }
}
