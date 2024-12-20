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

public class MeHandler extends BasicHandler {
    DataBase dataBase;
    Security security;

    public MeHandler(DataBase dataBase, Security security) {
        this.dataBase = dataBase;
        this.security = security;
    }


    // Json body with username and password
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.allowCORS(exchange);

        String method = exchange.getRequestMethod();
        if (method.equals("POST")) {
            String header = exchange.getRequestHeaders().getFirst("Authorization");
            String token = security.getTokenFromHeader(header);

            Identity identity = security.checkToken(token);
            JSONObject responseJSON = new JSONObject();

            PrivateUser user;
            try {
                 user = dataBase.getUser(identity.getUserId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            responseJSON.put("user", user.toJson());
            byte[] response = responseJSON.toString().getBytes();

            this.sendResponse(exchange, response);
        } else {
            exchange.sendResponseHeaders(405, 0);
        }
        exchange.close();
    }
}
