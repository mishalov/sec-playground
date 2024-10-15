package org.example.http.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.DataBase;
import org.example.dto.PublicUser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetUsersHandler implements HttpHandler {
    private DataBase db;
    public GetUsersHandler(DataBase db) {
        this.db = db;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException, IOException {
        ArrayList<PublicUser> users;
        try {
            users = db.getUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (PublicUser user : users) {
            jsonArray.put(user.toJson());
        }
        String response = jsonArray.toString();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}
