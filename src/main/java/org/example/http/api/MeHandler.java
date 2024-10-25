package org.example.http.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.DataBase;
import org.example.Security;
import org.example.utils.Identity;
import org.json.JSONObject;

import java.io.IOException;

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
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            String header = exchange.getRequestHeaders().getFirst("Authorization");
            String token = security.getTokenFromHeader(header);

            Identity identity = security.checkToken(token);
            JSONObject responseJSON = new JSONObject();
            responseJSON.put("user", identity.getUser().toJson()d);
            byte[] response = responseJSON.toString().getBytes();

            this.sendResponse(exchange, response);
        } else {
            exchange.sendResponseHeaders(405, 0);
        }
        exchange.close();
    }
}
