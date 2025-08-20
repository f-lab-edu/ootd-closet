package project.closet.batch.config;

import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import project.closet.api.WeatherApiCallFailedException;
import project.closet.weather.entity.Weather;
import project.closet.weatherlocation.WeatherLocation;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CoordinateReadJobConfig {

    private final TaskExecutor weatherExecutor;
    private final ItemProcessor<WeatherLocation, List<Weather>> itemProcessor;
    private final ItemWriter<List<Weather>> weatherDataWriter;

    @Bean
    public Job coordinateJob(
        JobRepository jobRepository,
        Step coordinateReadStep
    ) {
        return new JobBuilder("COORDINATE_READ_JOB", jobRepository)
            .start(coordinateReadStep)
            .build();
    }

    @Bean
    @JobScope
    public Step coordinateReadStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemReader<WeatherLocation> weatherDataReader
    ) {
        return new StepBuilder("coordinateReadStep", jobRepository)
            .<WeatherLocation, Future<List<Weather>>>chunk(10, transactionManager)
            .reader(weatherDataReader)
            .processor(asyncItemProcessor())
            .writer(asyncItemWriter())
            .faultTolerant()
            .skipLimit(10)
            .skip(WeatherApiCallFailedException.class)
            .build();
    }

    @Bean
    public ItemReader<WeatherLocation> weatherDataReader(
        EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<WeatherLocation>()
            .name("weatherDataReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT w FROM WeatherLocation w ORDER BY w.id ASC")
            .pageSize(20)
            .saveState(true)
            .transacted(false)
            .build();
    }

    @Bean
    public AsyncItemProcessor<WeatherLocation, List<Weather>> asyncItemProcessor() {
        AsyncItemProcessor<WeatherLocation, List<Weather>> asyncProcessor = new AsyncItemProcessor<>();
        asyncProcessor.setDelegate(itemProcessor);
        asyncProcessor.setTaskExecutor(weatherExecutor);
        return asyncProcessor;
    }

    @Bean
    public AsyncItemWriter<List<Weather>> asyncItemWriter() {
        AsyncItemWriter<List<Weather>> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(weatherDataWriter);
        return asyncItemWriter;
    }

}
