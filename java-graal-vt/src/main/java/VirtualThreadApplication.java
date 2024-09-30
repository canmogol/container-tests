import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class VirtualThreadApplication implements HttpHandler {


    public static void main(String[] args) throws IOException {
        final VirtualThreadApplication application = new VirtualThreadApplication();
        application.start();
    }

    private void start() throws IOException {
        final InetSocketAddress serverAddress = new InetSocketAddress("0.0.0.0", 8080);
        final HttpServer localhost = HttpServer.create(serverAddress, 100);
        localhost.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        localhost.createContext("/", this);
        localhost.start();
        log("Server started.");
    }

    private static void log(String message) {
        System.out.println(message);
    }

    @Override
    public void handle(HttpExchange exchange) {
        handleRequest(exchange);
    }

    private void handleRequest(final HttpExchange exchange) {
        try {
            final String responseMessage = "Hello world!";
            exchange.sendResponseHeaders(200, responseMessage.length());
            exchange.getResponseBody().write(responseMessage.getBytes(StandardCharsets.UTF_8));
            exchange.getResponseBody().close();
        } catch (Exception e) {
            log("Error; %s".formatted(e.getMessage()));
        }
    }

}
