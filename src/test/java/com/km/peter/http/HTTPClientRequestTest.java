package com.km.peter.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class HTTPClientRequestTest {

    @Test
    public void testHttpGet() throws JsonProcessingException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HTTPClientRequest request = new HTTPClientRequest();
        Response response = request.httpGet("http://www.baidu.com", null, null);
        System.out.println(response.getData());
    }

    @Test
    public void testHttpPost() throws JsonProcessingException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HTTPClientRequest request = new HTTPClientRequest();
        Response response = request.httpPost("https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN", null, null);
        System.out.println(response.getData());
    }

    @Test
    public void testGet() throws InstantiationException, IllegalAccessException {
        Request request = RequestFactory.instance(HTTPClientRequest.class);
        Response response = request.get("http://www.baidu.com");
        System.out.println(response.getData());
    }

    @Test
    public void testGetWithQuery() throws InstantiationException, IllegalAccessException {
        Request request = RequestFactory.instance(HTTPClientRequest.class);
        Map<String, Object> query = new HashMap<>();
        query.put("access_token", "access_token");
        query.put("media_id", "media_id");
        Response response = request.get("https://api.weixin.qq.com/cgi-bin/media/get", query);
        System.out.println(response.getData());
    }

    @Test
    public void testPost() throws InstantiationException, IllegalAccessException {
        Request request = RequestFactory.instance(HTTPClientRequest.class);
        Map<String, Object> query = new HashMap<>();
        query.put("access_token", "access_token");
        Map<String, Object> params = new HashMap<>();
        params.put("openid", "oDF3iY9ffA-hqb2vVvbr7qxf6A0Q");
        params.put("remark", "pangzi");
        Response response = request.post("https://api.weixin.qq.com/cgi-bin/user/info/updateremark", query, params);
        System.out.println(response.getData());
    }


}
