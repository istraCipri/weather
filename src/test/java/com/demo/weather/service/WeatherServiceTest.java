package com.demo.weather.service;

import com.demo.weather.model.WeatherData;
import com.demo.weather.model.WeatherResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

    @Test
    public void testCalculateAverages() {
        WeatherService weatherServiceMock = mock(WeatherService.class);
        List<String> cities = Arrays.asList("Cluj-Napoca", "Medias");
        WeatherResponse weatherResponse = new WeatherResponse();
        List<WeatherData> weatherDataList = Arrays.asList(new WeatherData("Cluj-Napoca", 15.0, 25.0));
        weatherResponse.setResult(weatherDataList);
        when(weatherServiceMock.getWeatherData(cities)).thenReturn(weatherResponse);

        WeatherResponse responseEntity = weatherServiceMock.getWeatherData(cities);
        assertNotNull(responseEntity);
        assertEquals(weatherResponse, responseEntity);
    }
}
