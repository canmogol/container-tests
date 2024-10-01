package com.example.graal_vt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

import static java.util.Objects.isNull;

@SpringBootApplication
public class GraalVtApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraalVtApplication.class, args);
    }

}

@RestController
class VirtualThreadController {

    private final static HttpClient httpClient = HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build();

    @GetMapping("/")
    public String hello() {
        return "Hello world!";
    }

    @GetMapping("/external")
    public String external() {
        String externalUrl = System.getenv("EXTERNAL_URL");
        if (isNull(externalUrl)) {
            String error = "EXTERNAL_URL environment variable is not set.";
            log(error);
            throw new RuntimeException(error);
        }
        HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(externalUrl)).GET().build();
        try {
            final HttpResponse<String> request = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            return request.body();
        } catch (Exception e) {
            log("Error; %s".formatted(e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    private void log(String message) {
        System.out.println(message);
    }
}