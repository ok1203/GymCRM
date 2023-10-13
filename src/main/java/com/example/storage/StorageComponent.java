package com.example.storage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;

@Component
public class StorageComponent {

    private JSONParser parser = new JSONParser();
    private JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/file.json"));
    private JSONArray array;

    public StorageComponent() throws IOException, ParseException {
    }

    public JSONArray getArray(String key) {
        array = (JSONArray) jsonObject.get(key);
        return array;
    }
}
