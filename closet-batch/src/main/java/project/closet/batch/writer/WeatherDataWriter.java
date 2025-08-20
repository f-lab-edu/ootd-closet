package project.closet.batch.writer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import project.closet.weather.entity.Weather;
import project.closet.weather.repository.WeatherRepository;

@Slf4j
@Component
@RequiredArgsConstructor
@StepScope
public class WeatherDataWriter implements ItemWriter<List<Weather>> {

    private final WeatherRepository weatherRepository;

    @Override
    public void write(Chunk<? extends List<Weather>> chunk) throws Exception {
        List<Weather> flattened = chunk.getItems().stream()
            .flatMap(List::stream)
            .toList();
        if (flattened.isEmpty()) {
            log.debug("coordinate is null");
        } else {
            log.info("Writing weather size : {}", flattened.size());
            weatherRepository.saveAll(flattened);
        }
    }
}
