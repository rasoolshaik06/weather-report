package com.java.weatherapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.java.weatherapp.dto.WeatherAverageDTO;
import com.java.weatherapp.dto.WeatherMapDTO;
import com.java.weatherapp.dto.WeatherMapTimeDTO;

import springfox.documentation.spring.web.json.Json;


@Service
public class WeatherService {
	
	
    private final String URI = "http://api.openweathermap.org/data/2.5/forecast";
    private final String API_ID = "2f8a5ead7a0b2d28ef8b4da5da4b20eb";

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<?> weatherForecastAverage(String zip) {
        List<WeatherAverageDTO> result = new ArrayList<WeatherAverageDTO>();
        try {
            WeatherMapDTO weatherMap = this.restTemplate.getForObject(this.url(zip), WeatherMapDTO.class);
            
            for (LocalDate reference = LocalDate.now();
                 reference.isBefore(LocalDate.now().plusDays(2));
                 reference = reference.plusDays(1)) {
                 LocalDate ref = reference;
                List<WeatherMapTimeDTO> collect = weatherMap.getList().stream()
                        .filter(x -> x.getDt().toLocalDate().equals(ref)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    result.add(this.average(collect));
                }
                
            }
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(new Json(e.getResponseBodyAsString()), e.getStatusCode());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private WeatherAverageDTO average(List<WeatherMapTimeDTO> list) {
        WeatherAverageDTO result = new WeatherAverageDTO();

        for (WeatherMapTimeDTO item : list) {
            result.setDate(item.getDt().toLocalDate());
           result.plusMap(item);
        }

        result.totalize();

        return result;
    }
 
    private String url(String zip) {
        return String.format(URI.concat("?q=%s").concat("&appid=%s").concat("&units=metric"), zip, API_ID);
    }
}
