package project.closet.batch.config;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.PlatformTransactionManager;
import project.closet.batch.api.WeatherApiCallFailedException;
import project.closet.weather.entity.Weather;
import project.closet.weatherlocation.WeatherLocation;
import project.closet.weatherlocation.WeatherLocationRepository;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CoordinateReadJobConfig {

    private static final int CHUNK_SIZE = 50;
    public static final String COORDINATE_READ_JOB = "COORDINATE_READ_JOB";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final WeatherLocationRepository weatherLocationRepository;

    private final ItemProcessor<WeatherLocation, List<Weather>> itemProcessor;
    private final ItemWriter<List<Weather>> weatherDataWriter;
    private final TaskExecutor weatherExecutor;

    @Bean
    public Job coordinateJob(Step coordinateReadStep) {
        return new JobBuilder(COORDINATE_READ_JOB, jobRepository)
            .start(coordinateReadStep)
            .build();
    }

    @Bean
    @JobScope
    public Step coordinateReadStep(ItemReader<WeatherLocation> weatherLocationReader) {
        return new StepBuilder("coordinateReadStep", jobRepository)
            .<WeatherLocation, Future<List<Weather>>>chunk(CHUNK_SIZE, transactionManager)
            .reader(weatherLocationReader)
            .processor(asyncItemProcessor()) // 일반 ItemProcessor 사용
            .writer(asyncItemWriter())
            .faultTolerant()    // 여기서 재시도, 예외, 스킵 등하자.
            .skipLimit(10)
            .skip(WeatherApiCallFailedException.class)
            .build();
    }


    @Bean
    @StepScope
    public RepositoryItemReader<WeatherLocation> weatherLocationReader() {
        return new RepositoryItemReaderBuilder<WeatherLocation>()
            .name("weatherLocationReader")
            .repository(weatherLocationRepository)
            .methodName("findAll")
            .pageSize(CHUNK_SIZE)
            .arguments(Collections.emptyList())
            .sorts(Collections.singletonMap("id", Direction.DESC))
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
