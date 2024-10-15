package org.example.http.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.DataBase;
import org.example.Security;
import org.example.dto.PrivateUser;
import org.example.utils.Identity;
import org.json.JSONObject;

import java.io.IOException;

public class LoginHandler implements HttpHandler {
    DataBase dataBase;
    Security security;

    public LoginHandler(DataBase dataBase, Security security) {
        this.dataBase = dataBase;
        this.security = security;
    }


    // Json body with username and password
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("POST")) {
            String body = new String(exchange.getRequestBody().readAllBytes());
            JSONObject json = new JSONObject(body);

            String username = json.getString("username");
            String password = json.getString("password");

            Identity identity = security.authenticate(username, password);

            if (identity == null) {
                exchange.sendResponseHeaders(401, 0);
                exchange.close();
                return;
            }

            JSONObject responseJSON = new JSONObject();
            responseJSON.put("token", identity.getToken());
            byte[] response = responseJSON.toString().getBytes();

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);

            exchange.close();
        } else {
            exchange.sendResponseHeaders(405, 0);
        }
        exchange.close();
    }
}
