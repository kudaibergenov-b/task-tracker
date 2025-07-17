package com.kudaibergenov.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalApiService {

    private final RestTemplate restTemplate;

    private final Environment environment;

    public ResponseEntity<String> fetchAndLogObjects() {
        ResponseEntity<String> response = restTemplate
                .getForEntity(Objects.requireNonNull(environment.getProperty("api.url")), String.class);

        log.info("Response from external API: {}", response.getBody());

        return response;
    }
}