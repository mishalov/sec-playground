package org.example.dto;

import org.json.JSONObject;

public class PublicUser {
    public int id;
    public String name;

    public PublicUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        return jsonObject;
    }
}
