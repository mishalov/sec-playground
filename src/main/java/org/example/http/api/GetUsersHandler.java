package org.example.http.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.DataBase;
import org.example.dto.PublicUser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetUsersHandler extends BasicHandler {
    private DataBase db;
    public GetUsersHandler(DataBase db) {
        this.db = db;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException, IOException {
        this.allowCORS(exchange);

        ArrayList<PublicUser> users;
        String query = exchange.getRequestURI().getQuery();
        String search = null;

        if (query != null) {
            search = java.net.URLDecoder.decode(this.getQueryMap(query).get("search"), StandardCharsets.UTF_8);;
        }

        try {
            users = db.getUsers(search);
            JSONArray jsonArray = new JSONArray();
            for (PublicUser user : users) {
                jsonArray.put(user.toJson());
            }
            String response = jsonArray.toString();

            this.sendResponse(exchange, response.getBytes());
        } catch (SQLException e) {
            this.sendException(exchange, e);
        }
    }
}
