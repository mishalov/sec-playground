package org.example.http.api;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class PingHandler extends BasicHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.allowCORS(exchange);

        String method = exchange.getRequestMethod();
        //print
        System.out.println("PingHandler: " + method.toUpperCase() + " " + exchange.getRequestURI());
        if (method.equals("GET")) {
            byte[] response = "Pong".getBytes();
            this.sendResponse(exchange, response);
        } else {
            exchange.sendResponseHeaders(405, 0);
        }
        exchange.close();
    }
}
