package org.example.dto;

import org.json.JSONObject;

public class PublicUser {
    public int id;
    public String name;
    public String avatar;

    public PublicUser(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("avatar", avatar);
        return jsonObject;
    }
}
