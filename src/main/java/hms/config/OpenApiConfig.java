package hms.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI hospitalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("Hospital management system API")
                .description("REST API for managing patients in hospital system")
                .version("v1.0"));
    }
}
