package project.closet.batch.config;

import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job coordinateJob;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void runCoordinateJob() throws Exception {
        LocalDate batchDate = LocalDate.now(ZoneId.of("Asia/Seoul"));

        JobParameters params = new JobParametersBuilder()
            .addString("batchDate", batchDate.toString())
            .toJobParameters();

        jobLauncher.run(coordinateJob, params);
    }
}
