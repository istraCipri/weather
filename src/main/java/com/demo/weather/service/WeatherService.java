package com.demo.weather.service;

import com.demo.weather.model.WeatherData;
import com.demo.weather.model.WeatherForecast;
import com.demo.weather.model.WeatherForecastDay;
import com.demo.weather.model.WeatherResponse;
import com.demo.weather.util.CsvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private static final List<String> ALLOWED_CITIES = Arrays.asList("Cluj-Napoca", "Bucuresti", "Timisoara", "Constanta", "Baia-Mare", "Arad");
    private final WebClient webClient;

    @Autowired
    public WeatherService(WebClient.Builder webClientBuilder, @Value("${external.api.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    private static boolean isValidCity(String city) {
        if (!ALLOWED_CITIES.contains(city)) {
            return false;
        }
        return true;
    }

    private static boolean isNullWeatherForecast(WeatherForecast weatherForecast) {
        if (weatherForecast == null) {
            return true;
        } else if (weatherForecast.getTemperature() == null
                && weatherForecast.getWind() == null
                && weatherForecast.getDescription() == null
                && (weatherForecast.getForecast() == null || weatherForecast.getForecast().isEmpty())) {
            return true;
        }
        return false;
    }

    private static WeatherResponse calculateAverages(Map<String, WeatherForecast> forecastMap) {
        List<WeatherData> weatherDataList = new ArrayList<>();
        WeatherResponse weatherResponse = new WeatherResponse();
        for (Map.Entry<String, WeatherForecast> entry : forecastMap.entrySet()) {
            String cityName = entry.getKey();
            WeatherForecast weatherForecast = entry.getValue();
            if (isNullWeatherForecast(weatherForecast)) {
                weatherDataList.add(new WeatherData(cityName, 0D, 0D));
                continue;
            }
            double temperatureSum = weatherForecast.getTemperature();
            double windSum = weatherForecast.getWind();
            if (weatherForecast.getForecast() != null) {
                for (WeatherForecastDay day : weatherForecast.getForecast()) {
                    temperatureSum += day.getTemperature();
                    windSum += day.getWind();
                }
            }
            double temperatureAverage = temperatureSum / 7;
            double windAverage = windSum / 7;

            weatherDataList.add(new WeatherData(cityName, temperatureAverage, windAverage));
        }
        weatherResponse.setResult(weatherDataList);
        return weatherResponse;
    }


    public WeatherResponse getWeatherData(List<String> cityList) {
        logger.info("Received a request to get weather data for cities: {}", cityList);
        List<String> validCityList = cityList.stream().filter(WeatherService::isValidCity).distinct().collect(Collectors.toList());
        logger.info("Valid cities: {}", validCityList);
        Map<String, WeatherForecast> forecastMap = validCityList
                .stream()
                .collect(
                        Collectors.toMap(
                                cityName -> cityName,
                                cityName -> getWeatherDataForCity(cityName),
                                (existing, replacement) -> replacement,
                                () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER)

                        )
                );
        WeatherResponse weatherResponse = calculateAverages(forecastMap);
        logger.info("Returning weather data response: {}", weatherResponse);
        CsvUtils.saveWeatherDataToCSV(weatherResponse.getResult());
        return weatherResponse;
    }


    private WeatherForecast getWeatherDataForCity(String city) {
        try {
            return webClient
                    .get()
                    .uri("/{city_name}", city)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(WeatherForecast.class)
                    .block();
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("Weather data not found for city: {}", city);
            }
        }
        return new WeatherForecast();
    }
}

