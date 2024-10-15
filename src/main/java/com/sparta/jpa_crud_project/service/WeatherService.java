package com.sparta.jpa_crud_project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }

    public String getTodayWeather() {
        String url = "https://f-api.github.io/f-api/weather.json";
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
        if (response != null && response.getWeather() != null) {
            return response.getWeather();
        }
        return "Weather data not available";
    }

    // 내부 클래스 또는 별도의 파일로 정의
    public static class WeatherResponse {
        private String weather;

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }
    }
}
