package org.statistic.eggs.core.service;

import org.statistic.eggs.core.dao.StatisticDao;
import org.statistic.eggs.core.entity.Settings;
import org.statistic.eggs.handler.ErrorHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class WeatherService {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";
    private static String apiKey;
    private static String city;

    public static String getWeather() {
        try {
            List<Settings> settings = StatisticDao.getAllSettings();
            for (Settings s : settings) {
                if (s.getWeatherSettings() != null) {
                    apiKey = s.getWeatherSettings().getApiKey();
                    city = s.getWeatherSettings().getCity();
                }
            }
            if (apiKey != null && city !=null) {
                String url = String.format(BASE_URL, city, apiKey);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                return response.body();
            } else {
                return null;
            }

        } catch (Exception e) {
            ErrorHandler.showErrorDialog("Could not reach weather API",e);
            return null;
        }
    }
}
