package project.closet.batch.config;

import java.time.Duration;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        ClientHttpRequestFactorySettings clientHttpRequestFactorySettings =
            ClientHttpRequestFactorySettings.defaults()
                .withConnectTimeout(Duration.ofSeconds(5))
                .withReadTimeout(Duration.ofSeconds(5));

        return restTemplateBuilder
            .requestFactorySettings(clientHttpRequestFactorySettings)
            .build();
    }

}
