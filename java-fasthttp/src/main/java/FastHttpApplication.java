import io.fusionauth.http.server.HTTPListenerConfiguration;
import io.fusionauth.http.server.HTTPServer;
import io.fusionauth.http.server.HTTPHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

import static java.util.Objects.isNull;

public class FastHttpApplication {

    private static final HttpClient httpClient = HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build();

    public static void main(String[] args) throws IOException {
        final FastHttpApplication application = new FastHttpApplication();
        application.start();
    }

    private void start() throws IOException {
        HTTPHandler handler = (req, res) -> {
            String externalUrl = System.getenv("EXTERNAL_URL");
            if (isNull(externalUrl)) {
                externalUrl = "http://172.17.0.1:9090";
            }
            String responseMessage = "Not Found";
            int statusCode = 404;
            if ("/external".equals(req.getPath()) && "GET".equals(req.getMethod().name())) {
                HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(externalUrl)).GET().build();
                try {
                    final HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
                    responseMessage = response.body();
                    statusCode = 200;
                } catch (InterruptedException e) {
                    log("Error; %s".formatted(e.getMessage()));
                    responseMessage = "Internal Server Error";
                    statusCode = 500;
                }
            } else if ("/".equals(req.getPath())) {
                responseMessage = "Hello from Fast HTTP Server!";
                statusCode = 200;
            }
            byte[] bytes = responseMessage.getBytes(StandardCharsets.UTF_8);
            res.setStatus(statusCode);
            res.setHeader("Content-Type", "text/plain; charset=UTF-8");
            res.setContentLength(bytes.length);
            res.getOutputStream().write(bytes);
        };
        int port = 8080;
        HTTPServer server = new HTTPServer().withHandler(handler)
                .withListener(new HTTPListenerConfiguration(port));
        server.start();
        log("Server started on port %d".formatted(port));
    }

    private static void log(String message) {
        System.out.println(message);
    }

}
