package apshomebe.caregility.com.websocket.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Aps-Home-API-Documentation")
                        .description("API Documentation For Aps-Home")
                        .contact(new Contact().email("contact-us@pratyin.com"))
                        .version("1.0")

                );
    }
}