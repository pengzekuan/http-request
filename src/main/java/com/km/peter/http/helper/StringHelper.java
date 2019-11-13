package com.km.peter.http.helper;

import java.util.Map;

public class StringHelper {

    public static String queryBuild(Map<String, Object> query) {
        if (query == null || query.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String key : query.keySet()) {
            if (key != null && query.get(key) != null) {
                builder.append(key)
                        .append("=")
                        .append(query.get(key))
                        .append("&");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static String urlBuilder(String uri, String queryString) {
        return queryString != null && queryString.length() > 0
                ? uri + "?" + queryString
                : uri;
    }
}
