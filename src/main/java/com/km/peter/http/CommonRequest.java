package com.km.peter.http;

import com.km.peter.http.enums.ContentType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

abstract class CommonRequest implements Request {

    static final int CONNECTION_TIMEOUT = 10000;

    static final int READ_TIMEOUT = 30000;

    static final ContentType CONTENT_TYPE = ContentType.JSON;

    static final Charset CHARSET = StandardCharsets.UTF_8;

    static String queryBuild(Map<String, Object> query) {
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

    @Override
    public Response get(String uri, Map<String, Object> query) {

        return this.request(uri, RequestMethod.GET, query, null);
    }

    @Override
    public Response get(String uri) {
        return this.request(uri, RequestMethod.GET, null, null);
    }

    @Override
    public Response get(String uri, Map<String, String> header, Map<String, Object> query) {
        return null;
    }

    @Override
    public Response post(String uri) {
        return this.request(uri, RequestMethod.POST, null, null);
    }

    @Override
    public Response post(String uri, Map<String, Object> query) {
        return this.request(uri, RequestMethod.POST, query, null);
    }

    @Override
    public Response post(String uri, Map<String, Object> query, Object params) {
        return this.request(uri, RequestMethod.POST, query, params);
    }

    @Override
    public Object patch() {
        return null;
    }

    @Override
    public Object put() {
        return null;
    }

    @Override
    public Object delete() {
        return null;
    }

    @Override
    public Object head() {
        return null;
    }

    @Override
    public Object connect() {
        return null;
    }

    @Override
    public Object options() {
        return null;
    }

    @Override
    public Object trace() {
        return null;
    }

    @Override
    public Response request(String uri, String method, Map<String, Object> query, Object params) {
        return null;
    }

}
