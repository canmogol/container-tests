package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

import static java.util.Objects.isNull;

@Path("/")
public class GreetingResource {

    private final HttpClient httpClient = HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build();

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/external")
    @Produces(MediaType.TEXT_PLAIN)
    public String external() {
        String externalUrl = System.getenv("EXTERNAL_URL");
        if (isNull(externalUrl)) {
            externalUrl = "http://172.17.0.1:9090";
        }
        HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(externalUrl)).GET().build();
        try {
            final HttpResponse<String> request = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            return request.body();
        } catch (Exception e) {
            System.out.println("Error; %s".formatted(e.getMessage()));
            throw new RuntimeException(e);
        }
    }

}
