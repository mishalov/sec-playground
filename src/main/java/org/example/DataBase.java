package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.example.dto.PrivateUser;
import org.example.dto.PublicUser;
import org.json.JSONArray;

public class DataBase {
    Connection conn = null;

    public DataBase() throws SQLException, ClassNotFoundException {
        String username = "testDb";
        String password = "testDb";
        String jdbcUrl = "jdbc:postgresql://localhost:5432/testDb";

        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        Class.forName("org.postgresql.Driver");
        this.conn = DriverManager.getConnection(jdbcUrl, props);
    }

    public void reset(){
        try {
            conn.createStatement().execute("DROP TABLE IF EXISTS users");
            conn.createStatement().execute("CREATE TABLE users (id SERIAL PRIMARY KEY, name VARCHAR(255), phone VARCHAR(255), address VARCHAR(255), password VARCHAR(255), department VARCHAR(255))");

            String jsonObject = Files.readString(Path.of("./src/main/java/resources/users.json"));
            JSONArray jsonArray = new JSONArray(jsonObject);

            for(int i = 0; i < jsonArray.length(); i++){
                String name = jsonArray.getJSONObject(i).getString("name");
                String phone = jsonArray.getJSONObject(i).getString("phone");
                String address = jsonArray.getJSONObject(i).getString("address");
                String password = Security.encrypt(jsonArray.getJSONObject(i).getString("password"));
                String department = jsonArray.getJSONObject(i).getString("department");

                conn.createStatement().execute("INSERT INTO users (name, phone, address, password, department) VALUES ('" + name + "', '" + phone + "', '" + address + "', '" + password + "', '" + department + "')");
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<PublicUser> getUsers() throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
        ArrayList<PublicUser> users = new ArrayList<>();
        while(rs.next()){
            users.add(new PublicUser(rs.getInt("id"), rs.getString("name")));
        }
        return users;
    }

    public PrivateUser getUser(int id) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users WHERE id = " + id);
        rs.next();
        return new PrivateUser(rs.getInt("id"), rs.getString("name"), rs.getString("phone"), rs.getString("address"));
    }

    public PrivateUser authenticate(String name, String password) throws SQLException, NoSuchAlgorithmException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users WHERE name = '" + name + "' AND password = '" + password + "'");

        if (!rs.next()) {
            return null;
        }

        return new PrivateUser(rs.getInt("id"), rs.getString("name"), rs.getString("phone"), rs.getString("address"));
    }
}
