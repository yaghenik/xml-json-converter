package service;

public class ManualConverter {

    // ================= XML → JSON =================

    public String xmlToJson(String xml) {
        try {
            xml = xml.replaceAll(">\\s+<", "><").trim();
            return parseXml(xml);
        } catch (Exception e) {
            return "Invalid XML";
        }
    }

    private String parseXml(String xml) {
        if (!xml.startsWith("<")) return "";

        String tag = xml.substring(1, xml.indexOf(">"));
        String endTag = "</" + tag + ">";
        String inner = xml.substring(xml.indexOf(">") + 1, xml.lastIndexOf(endTag));

        // Simple value
        if (!inner.contains("<")) {
            return "{ \"" + tag + "\" : \"" + inner + "\" }";
        }

        // Nested tags
        StringBuilder json = new StringBuilder();
        json.append("{ \"").append(tag).append("\" : {");

        while (inner.contains("<")) {
            int start = inner.indexOf("<");
            int end = inner.indexOf(">", start);
            String childTag = inner.substring(start + 1, end);
            String childEnd = "</" + childTag + ">";

            int childEndIndex = inner.indexOf(childEnd) + childEnd.length();
            String childFull = inner.substring(start, childEndIndex);

            String childJson = parseXml(childFull);
            childJson = childJson.substring(1, childJson.length() - 1);

            json.append(childJson).append(",");

            inner = inner.substring(childEndIndex).trim();
        }

        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("} }");
        return json.toString();
    }

    // ================= JSON → XML =================

    public String jsonToXml(String json) {
        try {
            json = json.replaceAll("\\s+", "");
            return parseJson(json);
        } catch (Exception e) {
            return "Invalid JSON";
        }
    }

    private String parseJson(String json) {
        // Remove outer braces
        json = json.substring(1, json.length() - 1);

        int colon = json.indexOf(":");
        String tag = json.substring(1, colon - 1);
        String value = json.substring(colon + 1);

        // Simple value
        if (!value.startsWith("{")) {
            value = value.replaceAll("\"", "");
            return "<" + tag + ">" + value + "</" + tag + ">";
        }

        // Nested object
        value = value.substring(1, value.length() - 1);
        StringBuilder xml = new StringBuilder();
        xml.append("<").append(tag).append(">");

        int braceCount = 0;
        int lastSplit = 0;

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);

            if (c == '{') braceCount++;
            if (c == '}') braceCount--;

            if (c == ',' && braceCount == 0) {
                String part = value.substring(lastSplit, i);
                xml.append(parseJson("{" + part + "}"));
                lastSplit = i + 1;
            }
        }

        // Last element
        String lastPart = value.substring(lastSplit);
        xml.append(parseJson("{" + lastPart + "}"));

        xml.append("</").append(tag).append(">");
        return xml.toString();
    }
}
