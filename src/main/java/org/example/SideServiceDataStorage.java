package org.example;

import java.util.*;

public class SideServiceDataStorage {
    private HashMap<String, ArrayList<String>> data = new HashMap<>();

    public void addData(String key, String value) {
        if (data.containsKey(key)) {
            data.get(key).add(value);
        } else {
            data.put(key, new ArrayList<>(List.of(value)));
        }
    }

    public List<String> getData(String key) {
        return data.get(key);
    }
}