package org.example.http.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.DataBase;
import org.example.Security;
import org.example.dto.PrivateUser;
import org.example.utils.Identity;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateUserHandler extends BasicHandler {
    DataBase dataBase;
    Security security;

    public UpdateUserHandler(DataBase dataBase, Security security) {
        this.dataBase = dataBase;
        this.security = security;
    }


    // Json body with username and password
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.allowCORS(exchange);

        String method = exchange.getRequestMethod();
        if (method.equals("PATCH")) {
            String header = exchange.getRequestHeaders().getFirst("Authorization");
            String token = security.getTokenFromHeader(header);
            Identity identity = security.checkToken(token);

            if (identity == null) {
                exchange.sendResponseHeaders(401, 0);
                exchange.close();
                return;
            }

            String body = new String(exchange.getRequestBody().readAllBytes());
            JSONObject json = new JSONObject(body);

            int id = identity.getUserId();

            String secret = json.getString("secret");
            String name = json.getString("name");

            PrivateUser user;
            try {
                user = dataBase.updateUser(id, name, secret);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            JSONObject responseJSON = new JSONObject();
            responseJSON.put("user", user.toJson());
            byte[] response = responseJSON.toString().getBytes();

            this.sendResponse(exchange, response);
        } else {
            exchange.sendResponseHeaders(405, 0);
        }
        exchange.close();
    }
}
