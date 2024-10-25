package org.example.http.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class BasicHandler implements HttpHandler {
    public void sendResponse(HttpExchange exchange, byte[] response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);

        exchange.close();
    }
}
