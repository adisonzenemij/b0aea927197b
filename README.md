# Guía de instalación y ejecución

Antes de iniciar, cree el archivo `.env` a partir de `.env.dist` y sustituya los valores de ejemplo, especialmente las credenciales de base de datos y los secretos JWT. No incluya secretos reales en el control de versiones.

## Requerimientos

| Herramienta | Versión | Enlace |
| --- | --- | --- |
| Java JDK | 26 | <https://www.oracle.com/java/technologies/downloads/#java26> |
| Maven | 3.9.x | <https://maven.apache.org/download.cgi> |

## Variables de Entorno del Proyecto

Las variables se definen en `.env`. Los prefijos identifican el ambiente: `DV_` (desarrollo/pruebas), `SG_` (staging) y `PD_` (producción). Configure únicamente las del perfil que vaya a ejecutar, sin eliminar las variables globales de APP.

### Propiedades de APP

| Variable | Significado | Valores o ejemplo |
| --- | --- | --- |
| `APP_NAME` | Nombre lógico de la aplicación. | `Aplicacion` |
| `APP_ENV` | Perfil de Spring usado por defecto. | `dev`, `stag` o `prod` |
| `LOG_ROOT` | Nivel de registro para el logger raíz. | `ALL`, `TRACE`, `DEBUG`, `INFO`, `WARN` o `ERROR` |
| `LOG_CODE` | Nivel de registro para las clases del paquete `code`. | `ALL`, `TRACE`, `DEBUG`, `INFO`, `WARN` o `ERROR` |
| `REDIS_PREFIX` | Prefijo común de las claves que la aplicación guarda en Redis. | `b0aea927197b` |
| `SONAR_HOST_URL` | URL del servidor SonarQube para la comprobación de estado. Es opcional. | `http://localhost:9000` |
| `SONAR_TOKEN` | Token de acceso a SonarQube. Se reserva para la integración externa; no debe publicarse. | Secreto |
| `SONAR_CTO` | Tiempo máximo de conexión a SonarQube. | `1s` |
| `SONAR_RTO` | Tiempo máximo de lectura desde SonarQube. | `2s` |
| `TIME_ZONE` | Zona horaria de referencia en formato IANA. | `America/Bogota` |
| `TZ_SOURCE` | Archivo fuente de la base de datos de zonas horarias. | `tzdb-2026c.tar.lz` |
| `CORS_ALLOWED_ORIGINS` | Orígenes permitidos para CORS, separados por comas y sin `/` final. Si se omite, se usan los orígenes locales configurados. | `https://app.example.com` |

### Propiedades de Test / desarrollo (`dev`)

| Variable | Significado | Valores o ejemplo |
| --- | --- | --- |
| `DV_DB_SERVICE` | Motor de base de datos seleccionado como referencia de configuración. | `MySQL`, `Oracle` o `PostgreSQL` |
| `DV_DB_USER` | Usuario de la base de datos de desarrollo. | `usuario` |
| `DV_DB_PASS` | Contraseña de la base de datos de desarrollo. | Secreto |
| `DV_DB_DRIVER` | Clase del controlador JDBC. | `com.mysql.cj.jdbc.Driver` |
| `DV_DB_URL` | URL JDBC de conexión a la base de datos. | `jdbc:mysql://127.0.0.1:3306/database` |
| `DV_JPA_DDL` | Estrategia de Hibernate para el esquema. | `none`, `validate`, `update`, `create` o `create-drop` |
| `DV_SRV_PORT` | Puerto HTTP de la aplicación. | `8001` |
| `DV_DOC` | Habilita OpenAPI, Swagger UI y Scalar. | `true` o `false` |
| `DV_JWT_SECRET` | Secreto, de al menos 32 caracteres, para firmar JWT de desarrollo. | Secreto aleatorio |
| `DV_JWT_EXPIRATION_SECONDS` | Vigencia de los JWT en segundos. | `3600` |
| `DV_JWT_ISSUER` | Emisor incluido en los JWT. | `b0aea927197b-dev` |
| `DV_REDIS_HOST` | Host del servidor Redis. | `127.0.0.1` |
| `DV_REDIS_PORT` | Puerto del servidor Redis. | `36371` |
| `DV_REDIS_FORMAT` | Formato usado para almacenar valores en Redis. | `JSON` o `BASE64` |
| `DV_ZIPKIN_PTCL` | Protocolo para conectarse a Zipkin. | `http` o `https` |
| `DV_ZIPKIN_HOST` | Host del servidor Zipkin. | `127.0.0.1` |
| `DV_ZIPKIN_PORT` | Puerto del servidor Zipkin. | `39411` |
| `DV_ZIPKIN_CTO` | Tiempo máximo de conexión a Zipkin. | `1s` |
| `DV_ZIPKIN_RTO` | Tiempo máximo de lectura desde Zipkin. | `1s` |
| `DV_ZIPKIN_SAMPLE` | Proporción de trazas enviada a Zipkin. | Valor entre `0.0` y `1.0` |

### Propiedades de Staging (`stag`)

| Variable | Significado | Valores o ejemplo |
| --- | --- | --- |
| `SG_DB_SERVICE` | Motor de base de datos seleccionado como referencia de configuración. | `MySQL`, `Oracle` o `PostgreSQL` |
| `SG_DB_USER` | Usuario de la base de datos de staging. | `usuario` |
| `SG_DB_PASS` | Contraseña de la base de datos de staging. | Secreto |
| `SG_DB_DRIVER` | Clase del controlador JDBC. | `com.mysql.cj.jdbc.Driver` |
| `SG_DB_URL` | URL JDBC de conexión a la base de datos. | `jdbc:mysql://host:3306/database` |
| `SG_JPA_DDL` | Estrategia de Hibernate para el esquema. | `none`, `validate`, `update`, `create` o `create-drop` |
| `SG_SRV_PORT` | Puerto HTTP de la aplicación. | `8005` |
| `SG_DOC` | Habilita OpenAPI, Swagger UI y Scalar. | `true` o `false` |
| `SG_JWT_SECRET` | Secreto, de al menos 32 caracteres, para firmar JWT de staging. | Secreto aleatorio |
| `SG_JWT_EXPIRATION_SECONDS` | Vigencia de los JWT en segundos. | `3600` |
| `SG_JWT_ISSUER` | Emisor incluido en los JWT. | `b0aea927197b-stag` |
| `SG_REDIS_HOST` | Host del servidor Redis. | `127.0.0.1` |
| `SG_REDIS_PORT` | Puerto del servidor Redis. | `36375` |
| `SG_REDIS_FORMAT` | Formato usado para almacenar valores en Redis. | `JSON` o `BASE64` |
| `SG_ZIPKIN_PTCL` | Protocolo para conectarse a Zipkin. | `http` o `https` |
| `SG_ZIPKIN_HOST` | Host del servidor Zipkin. | `127.0.0.1` |
| `SG_ZIPKIN_PORT` | Puerto del servidor Zipkin. | `39415` |
| `SG_ZIPKIN_CTO` | Tiempo máximo de conexión a Zipkin. | `1s` |
| `SG_ZIPKIN_RTO` | Tiempo máximo de lectura desde Zipkin. | `1s` |
| `SG_ZIPKIN_SAMPLE` | Proporción de trazas enviada a Zipkin. | Valor entre `0.0` y `1.0` |

### Propiedades de Producción (`prod`)

| Variable | Significado | Valores o ejemplo |
| --- | --- | --- |
| `PD_DB_SERVICE` | Motor de base de datos seleccionado como referencia de configuración. | `MySQL`, `Oracle` o `PostgreSQL` |
| `PD_DB_USER` | Usuario de la base de datos de producción. | `usuario` |
| `PD_DB_PASS` | Contraseña de la base de datos de producción. | Secreto |
| `PD_DB_DRIVER` | Clase del controlador JDBC. | `com.mysql.cj.jdbc.Driver` |
| `PD_DB_URL` | URL JDBC de conexión a la base de datos. | `jdbc:mysql://host:3306/database` |
| `PD_JPA_DDL` | Estrategia de Hibernate para el esquema. | Se recomienda `validate` o `none` |
| `PD_SRV_PORT` | Puerto HTTP de la aplicación. | `8009` |
| `PD_DOC` | Habilita OpenAPI, Swagger UI y Scalar. | `true` o `false`; normalmente `false` |
| `PD_JWT_SECRET` | Secreto, de al menos 32 caracteres, para firmar JWT de producción. | Secreto aleatorio gestionado de forma segura |
| `PD_JWT_EXPIRATION_SECONDS` | Vigencia de los JWT en segundos. | `3600` |
| `PD_JWT_ISSUER` | Emisor incluido en los JWT. | `b0aea927197b-prod` |
| `PD_REDIS_HOST` | Host del servidor Redis. | `127.0.0.1` |
| `PD_REDIS_PORT` | Puerto del servidor Redis. | `36379` |
| `PD_REDIS_FORMAT` | Formato usado para almacenar valores en Redis. | `JSON` o `BASE64` |
| `PD_ZIPKIN_PTCL` | Protocolo para conectarse a Zipkin. | `http` o `https` |
| `PD_ZIPKIN_HOST` | Host del servidor Zipkin. | `127.0.0.1` |
| `PD_ZIPKIN_PORT` | Puerto del servidor Zipkin. | `39419` |
| `PD_ZIPKIN_CTO` | Tiempo máximo de conexión a Zipkin. | `1s` |
| `PD_ZIPKIN_RTO` | Tiempo máximo de lectura desde Zipkin. | `1s` |
| `PD_ZIPKIN_SAMPLE` | Proporción de trazas enviada a Zipkin. | Valor entre `0.0` y `1.0` |

## Ejecución

Ejecute los comandos desde la raíz del proyecto, después de configurar `.env`.

| Acción | Comando |
| --- | --- |
| Limpiar artefactos generados | `mvn clean` |
| Ejecutar con el perfil por defecto (`dev`) | `mvn spring-boot:run` |
| Ejecutar con el perfil de desarrollo | `mvn spring-boot:run -Pdev` |
| Ejecutar con el perfil de staging | `mvn spring-boot:run -Pstag` |
| Ejecutar con el perfil de producción | `mvn spring-boot:run -Pprod` |

En Windows también puede reemplazar `mvn` por `./mvnw.cmd`; en macOS o Linux, use `./mvnw`. Estos scripts usan el Maven Wrapper incluido en el repositorio.
