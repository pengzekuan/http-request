package com.km.peter.http;

import java.util.Map;

public interface Request {

    Response get(String uri);

    Response get(String uri, Map<String, Object> query);

    Response get(String uri, Map<String, String> header, Map<String, Object> query);

    Object post();

    Object patch();

    Object put();

    Object delete();

    Object head();

    Object connect();

    Object options();

    Object trace();

    Response request(String uri, String method, Map<String, Object> query);

}
