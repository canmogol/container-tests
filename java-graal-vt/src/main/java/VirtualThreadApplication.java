import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

import static java.util.Objects.isNull;

public class VirtualThreadApplication {

    private final static HttpClient httpClient = HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build();

    public static void main(String[] args) throws IOException {
        final VirtualThreadApplication application = new VirtualThreadApplication();
        application.start();
    }

    private void start() throws IOException {
        final InetSocketAddress serverAddress = new InetSocketAddress("0.0.0.0", 8080);
        final HttpServer localhost = HttpServer.create(serverAddress, 100);
        localhost.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        localhost.createContext("/", exchange -> handleRequest(exchange, "Hello world!"));
        localhost.createContext("/external", exchange -> {
            String externalUrl = System.getenv("EXTERNAL_URL");
            if (isNull(externalUrl)) {
                String error = "EXTERNAL_URL environment variable is not set.";
                log(error);
                throw new RuntimeException(error);
            }
            HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(externalUrl)).GET().build();
            try {
                final HttpResponse<String> request = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
                handleRequest(exchange, request.body());
            } catch (InterruptedException e) {
                log("Error; %s".formatted(e.getMessage()));
                throw new RuntimeException(e);
            }
        });
        localhost.start();
        log("Server started.");
    }

    private void handleRequest(final HttpExchange exchange, final String responseMessage) {
        try {
            exchange.sendResponseHeaders(200, responseMessage.length());
            exchange.getResponseBody().write(responseMessage.getBytes(StandardCharsets.UTF_8));
            exchange.getResponseBody().close();
        } catch (Exception e) {
            log("Error; %s".formatted(e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }

}
