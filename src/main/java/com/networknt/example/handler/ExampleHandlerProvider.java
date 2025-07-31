package com.networknt.example.handler;

import com.networknt.handler.HandlerProvider;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Methods;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

import static java.util.Objects.isNull;


public class ExampleHandlerProvider implements HandlerProvider {

    private final static HttpClient httpClient = HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build();

    public HttpHandler getHandler() {
        return Handlers.routing()
                .add(Methods.GET, "/", exchange -> exchange.getResponseSender().send("Hello light4j"))
                .add(Methods.GET, "/external", new HttpHandler() {
                    public void handleRequest(HttpServerExchange exchange) throws Exception {
                        String externalUrl = System.getenv("EXTERNAL_URL");
                        if (isNull(externalUrl)) {
                            externalUrl = "http://192.168.106.1:9090";
                        }
                        HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(externalUrl)).GET().build();
                        try {
                            final HttpResponse<String> request = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
                            String body = request.body();
                            exchange.getResponseSender().send(body);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }
}
