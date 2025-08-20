package project.closet.weather.parser;

import java.util.List;
import project.closet.weather.entity.Weather;

public interface WeatherParser<I> {

    List<Weather> parseToWeatherEntities(I input);

}
