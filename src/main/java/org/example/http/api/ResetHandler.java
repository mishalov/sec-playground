package org.example.http.api;

import com.sun.net.httpserver.HttpExchange;
import org.example.DataBase;
import org.example.Security;

import java.io.IOException;

public class ResetHandler extends BasicHandler {
    DataBase dataBase;
    Security security;

    public ResetHandler(DataBase dataBase, Security security) {
        this.dataBase = dataBase;
        this.security = security;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.allowCORS(exchange);

        String method = exchange.getRequestMethod();


        if (method.equals("POST")) {
            this.dataBase.reset();
            this.security.reset();
            exchange.sendResponseHeaders(200, 0);
        }

        exchange.close();
    }
}
