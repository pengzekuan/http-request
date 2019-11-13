package com.km.peter.http;

import java.util.Map;

public interface Request {

    Response get(String uri);

    Response get(String uri, Map<String, Object> query);

    Response get(String uri, Map<String, Object> query, Map<String, String> header);

    Response post(String uri);

    Response post(String uri, Map<String, Object> query);

    Response post(String uri, Object params);

    Response post(String uri, Map<String, Object> query, Object params);

    Response post(String uri, Map<String, Object> query, Object params, Map<String, String> header);


    Object patch();

    Object put();

    Object delete();

    Object head();

    Object connect();

    Object options();

    Object trace();

    public Response request(String uri, String method, Map<String, Object> query, Object params, Map<String, String> headers);

}
