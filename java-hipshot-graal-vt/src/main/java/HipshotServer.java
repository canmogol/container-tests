import server.WebServer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

import static java.util.Objects.isNull;

public class HipshotServer {
    public static void main(String[] args) {
        new HipshotServer().start();
    }

    private void start() {
        String externalUrl = System.getenv("EXTERNAL_URL");
        if (isNull(externalUrl)) {
            externalUrl = "http://172.17.0.1:9090";
        }
        HttpClient httpClient = HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(externalUrl)).GET().build();
        WebServer.create((serverRequest, serverResponse) -> {
            if ("/external".equals(serverRequest.path())) {
                try {
                    final HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    String response = httpResponse.body();
                    serverResponse.header("Content-Type", "text/html; charset=UTF-8")
                            .body(response)
                            .send();
                } catch (Exception e) {
                    System.out.println("Error calling external service: %s".formatted(e.getMessage()));
                }
            } else {
                serverResponse.header("Content-Type", "text/plain")
                        .body("Hello from Hipshot!")
                        .send();
            }
        }).start();
    }
}
