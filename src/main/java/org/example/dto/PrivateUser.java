package org.example.dto;

import org.json.JSONObject;

public class PrivateUser {
    public int id;
    public String name;
    public String phone;
    public String address;

    public PrivateUser(int id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        // Add fields
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("phone", phone);
        jsonObject.put("address", address);

        return jsonObject;
    }
}
