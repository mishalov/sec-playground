package org.example.http.api;

import com.sun.net.httpserver.HttpExchange;
import org.example.DataBase;
import org.example.SideServiceDataStorage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class SideServiceHandler  extends BasicHandler {
    SideServiceDataStorage sideService;

    public SideServiceHandler(SideServiceDataStorage sideService) {
        this.sideService = sideService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.allowCORS(exchange);

        String method = exchange.getRequestMethod();
        // Goes from  /side-service/{userId} to /side-service
        String userId = exchange.getRequestURI().getPath().split("/")[2];
        JSONArray responseJSON = new JSONArray();

        if (method.equals("POST")) {
            String body = new String(exchange.getRequestBody().readAllBytes());

            try {
                sideService.addData(userId, body);
                exchange.sendResponseHeaders(200, 0);
            } catch (Exception e) {
                exchange.sendResponseHeaders(400, 0);
                return;
            }

        }

        else if (method.equals("GET")) {
            List<String> data = sideService.getData(userId);


            for (String item : data) {
                responseJSON.put(item);
            }

            byte[] response = responseJSON.toString().getBytes();
            this.sendResponse(exchange, response);
        }

        else {
            exchange.sendResponseHeaders(405, 0);
        }
    }
}
