package io.github.ignamlrz.autotrader.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        // Information about API
         Info info = new Info()
                 .title("Ignamlrz Autotrader")
                 .description("Autotrader bot which makes notices and even allows trading in the market");

         // Return OpenAPI model
         return new OpenAPI()
                 .info(info);
    }
}
