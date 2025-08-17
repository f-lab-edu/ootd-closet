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
import project.closet.weather.entity.Weather;
import project.closet.weather.repository.WeatherRepository;
import project.closet.weatherlocation.WeatherLocation;
import project.closet.weatherlocation.WeatherLocationRepository;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CoordinateReadJobConfig {

    public static final String ID = "id";
    public static final int CHUNK_SIZE = 100;
    public static final String COORDINATE_READ_JOB = "COORDINATE_READ_JOB";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final WeatherLocationRepository weatherLocationRepository;
    private final WeatherRepository weatherRepository;

    @Bean
    public Job coordinateJob(Step coordinateReadStep) {
        return new JobBuilder(COORDINATE_READ_JOB, jobRepository)
            .start(coordinateReadStep)
            .build();
    }

    @Bean
    public AsyncItemProcessor<WeatherLocation, List<Weather>> asyncProcessor(
        ItemProcessor<WeatherLocation, List<Weather>> delegate,
        TaskExecutor weatherExecutor
    ) {
        AsyncItemProcessor<WeatherLocation, List<Weather>> processor = new AsyncItemProcessor<>();
        processor.setDelegate(delegate);
        processor.setTaskExecutor(weatherExecutor);
        return processor;
    }

    @Bean
    public AsyncItemWriter<List<Weather>> asyncWriter(ItemWriter<List<Weather>> delegate) {
        AsyncItemWriter<List<Weather>> writer = new AsyncItemWriter<>();
        writer.setDelegate(loggingWriter());
        return writer;
    }

    @Bean
    @JobScope
    public Step coordinateReadStep(
        ItemReader<WeatherLocation> weatherLocationReader,
        AsyncItemProcessor<WeatherLocation, List<Weather>> asyncProcessor,
        AsyncItemWriter<List<Weather>> asyncWriter
    ) {
        return new StepBuilder("coordinateReadStep", jobRepository)
            .<WeatherLocation, Future<List<Weather>>>chunk(CHUNK_SIZE, transactionManager)
            .reader(weatherLocationReader)
            .processor(asyncProcessor)
            .writer(asyncWriter)
            .build();
    }

    @Bean
    @StepScope
    public ItemWriter<List<Weather>> loggingWriter() {
        return items -> {
            List<Weather> flattened = items.getItems().stream()
                .flatMap(List::stream)   // List<List<Weather>> → List<Weather>
                .toList();
            if (flattened.isEmpty()) {
                log.debug("coordinate is null");
                return;
            } else {
                weatherRepository.saveAll(flattened);
            }
        };
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
            .sorts(Collections.singletonMap(ID, Direction.DESC))
            .build();
    }
}
