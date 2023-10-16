package com.demo.weather.model;

import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {
    List<WeatherData> result;
}
