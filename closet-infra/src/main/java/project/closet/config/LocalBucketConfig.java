package project.closet.config;

import io.github.bucket4j.Bucket;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalBucketConfig {

    public static final int MONTHLY_TOKEN = 300_000;
    public static final int DAILY_TOKEN = 30_000;

    @Bean
    public Bucket bucket() {
        return Bucket.builder()
            .addLimit(limit -> limit
                .capacity(DAILY_TOKEN)
                .refillIntervally(DAILY_TOKEN, Duration.ofDays(1)))
            .addLimit(limit -> limit
                .capacity(MONTHLY_TOKEN)
                .refillIntervally(MONTHLY_TOKEN, Duration.ofDays(30)))
            .build();
    }
}
