package com.demo.weather.controller;

import com.demo.weather.model.WeatherResponse;
import com.demo.weather.service.WeatherService;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@Validated
public class WeatherController {

    private final WeatherService weatherService;
    private final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeatherAverages(@RequestParam(name = "city") @NotEmpty(message = "You need to insert at least one city.") List<String> city) {
        WeatherResponse weatherResponse = weatherService.getWeatherData(city);
        return ResponseEntity.ok(weatherResponse);
    }
}
