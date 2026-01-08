package service;

import org.json.JSONObject;
import org.json.XML;

public class ApiConverter {

    public String xmlToJson(String xml) {
        try {
            JSONObject json = XML.toJSONObject(xml);
            return json.toString(4);
        } catch (Exception e) {
            return "Invalid XML";
        }
    }

    public String jsonToXml(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            return XML.toString(obj);
        } catch (Exception e) {
            return "Invalid JSON";
        }
    }
}
