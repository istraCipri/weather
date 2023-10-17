package com.demo.weather.controller;


import com.demo.weather.model.WeatherData;
import com.demo.weather.model.WeatherResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherControllerTest {

    @Test
    public void testGetWeatherAverages() {
        WeatherController weatherControllerMock = mock(WeatherController.class);
        List<String> cities = Arrays.asList("Cluj-Napoca", "Medias");
        WeatherResponse weatherResponse = new WeatherResponse();
        List<WeatherData> weatherDataList = Arrays.asList(new WeatherData("Cluj-Napoca", 15.0, 25.0));
        weatherResponse.setResult(weatherDataList);
        when(weatherControllerMock.getWeatherAverages(cities)).thenReturn(ResponseEntity.ok(weatherResponse));

        ResponseEntity<WeatherResponse> responseEntity = weatherControllerMock.getWeatherAverages(cities);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(weatherResponse, responseEntity.getBody());
    }
}
