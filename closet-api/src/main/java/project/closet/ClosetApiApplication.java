package project.closet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
public class ClosetApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClosetApiApplication.class, args);
    }
}
