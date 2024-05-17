package com.agsr.task.runner;

import com.agsr.task.entity.Patient;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Slf4j
public class EntityGenerator {

    private static final String TOKEN_URL = "http://localhost:8080/realms/agsr/protocol/openid-connect/token";
    private static final String CLIENT_ID = "springsecurity";
    private static final String CLIENT_SECRET = "O9JNnP0Irzf5qhEB70Lq1SttANgFr4N5";
    private static final String API_URL = "http://localhost:8081/agsr/api/patients";
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        String accessToken = getAccessToken();
        if (accessToken != null) {
            generatePatients(accessToken);
        } else {
            log.info("Failed to obtain access token");
        }
    }

    private static String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("client_id", CLIENT_ID);
        map.add("username", "test");
        map.add("password", "admin");
        map.add("client_secret", CLIENT_SECRET);
        map.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        JSONObject response = restTemplate.postForEntity(TOKEN_URL, request, JSONObject.class).getBody();
        return response != null ? (String) response.get("access_token") : "";
    }

    private static void generatePatients(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        for (int i = 0; i < 100; i++) {
            Patient patient = new Patient();
            patient.setId(UUID.randomUUID().toString());
            patient.setName("Patient " + i);
            patient.setGender(RANDOM.nextBoolean() ? "male" : "female");
            patient.setBirthDate(new Date());

            HttpEntity<Patient> request = new HttpEntity<>(patient, headers);

            ResponseEntity<Patient> response = restTemplate.postForEntity(API_URL, request, Patient.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Created patient with ID: " + response.getBody().getId());
            } else {
                log.info("Failed to create patient " + i);
            }
        }
    }
}
