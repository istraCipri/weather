package com.demo.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecast {
    private Double temperature;
    private Double wind;
    private String description;
    private List<WeatherForecastDay> forecast;
}
