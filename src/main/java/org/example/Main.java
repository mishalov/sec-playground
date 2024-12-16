package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.http.api.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        InetSocketAddress address = new InetSocketAddress(8080);
        HttpServer server = HttpServer.create(address, 0);
        DataBase db = null;

        try {
            db = new DataBase();
            db.reset();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        if (db == null) {
            throw new RuntimeException("Database is not initialized");
        }
        Security security = new Security(db);

        // print
        try {
            server.createContext("/ping", new PingHandler());
            server.createContext("/users", new GetUsersHandler(db));
            server.createContext("/user", new UpdateUserHandler(db, security));
            server.createContext("/login", new LoginHandler(db, security));
            server.createContext("/me", new MeHandler(db, security));
            server.createContext("/reset", new ResetHandler(db, security));
            // Use this route as a route to some "fake service"
            server.createContext("/side-service", new SideServiceHandler(new SideServiceDataStorage()));

            // Printthat the server is running
            System.out.println("Server is running on port 8080");
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}