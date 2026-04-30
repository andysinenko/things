package ua.com.sinenko.things;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Home library and tools API",
        version = "1.0",
        description = "API for save information about my books and tools"
    ),
    servers = {
        @Server(url = "http://localhost:8080")
    }
)
public class ThingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThingsApplication.class, args);
	}

}
