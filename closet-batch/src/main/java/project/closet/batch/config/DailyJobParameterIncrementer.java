package project.closet.batch.config;

import java.time.LocalDate;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.stereotype.Component;

@Component
public class DailyJobParameterIncrementer implements JobParametersIncrementer {
    @Override
    public JobParameters getNext(JobParameters parameters) {
        return new JobParametersBuilder()
            .addString("jobDate", LocalDate.now().toString()) // 매일 날짜 주입
            .toJobParameters();
    }
}
