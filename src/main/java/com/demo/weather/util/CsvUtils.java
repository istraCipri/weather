package com.demo.weather.util;

import com.demo.weather.model.WeatherData;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

@UtilityClass
public class CsvUtils {
    private static final Logger logger = LoggerFactory.getLogger(CsvUtils.class);
    private static final String CSV_EXPORT_FILE_PATH = "weather-data.csv";

    public static void saveWeatherDataToCSV(List<WeatherData> weatherDataList) {
        try (PrintWriter printWriter = new PrintWriter(CSV_EXPORT_FILE_PATH, "UTF-8")) {
            printWriter.println("Name, temperature, wind");
            for (WeatherData weatherData : weatherDataList) {
                String weatherDataLine = String.format("%s,%s,%s", weatherData.getName(), weatherData.getTemperature(), weatherData.getWind());
                printWriter.println(weatherDataLine);
            }
            logger.info("Weather data saved to CSV file: {}", CSV_EXPORT_FILE_PATH);
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            logger.error("Error while saving the data to CSV file: {}", CSV_EXPORT_FILE_PATH);
        }
    }
}
