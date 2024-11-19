package org.example.dto;

import org.json.JSONObject;

public class PrivateUser {
    public int id;
    public String name;
    public String phone;
    public String address;
    public String secret;
    public String avatar;

    public PrivateUser(int id, String name, String phone, String address, String secret, String avatar) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.secret = secret;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        // Add fields
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("phone", phone);
        jsonObject.put("address", address);
        jsonObject.put("secret", secret);
        jsonObject.put("avatar", avatar);

        return jsonObject;
    }
}
