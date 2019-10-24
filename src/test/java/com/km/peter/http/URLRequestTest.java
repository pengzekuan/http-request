package com.km.peter.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class URLRequestTest {

    private static final String TEST_STORAGE = "D:\\test";

    @Test
    public void testRequest() throws JsonProcessingException {
        Request request = new URLRequest();
        Object res = request.request("http://www.baidu.com", RequestMethod.GET, null, null);
        System.out.println(new ObjectMapper().writeValueAsString(res));
    }

    @Test
    public void testGet() throws JsonProcessingException, UnsupportedEncodingException {
        Request request = new URLRequest();
        Response res = request.get("https://www.baidu.com");
        System.out.println(new ObjectMapper().writeValueAsString(res));
        System.out.println(res.getData());
    }

    @Test
    public void testGetImage() throws JsonProcessingException {
        Request request = new URLRequest();
        Response res = request.get("https://pic1.zhimg.com/v2-3b4fc7e3a1195a081d0259246c38debc_1200x500.jpg");
        byte[] bytes = res.getBytes();
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(TEST_STORAGE + File.separator + "test.jpg"));
            outputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(outputStream).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(new ObjectMapper().writeValueAsString(res));
    }

    @Test
    public void testGetWithParams() throws JsonProcessingException {
        Request request = new URLRequest();
        Map<String, Object> query = new HashMap<>();
        query.put("a", "a");
        query.put("b", 2);
        Object res = request.get("http://www.baidu.com", query);
        System.out.println(new ObjectMapper().writeValueAsString(res));
    }

    @Test
    public void testGetWithHeader() {

    }

    @Test
    public void testPost() throws JsonProcessingException {
        Request request = new URLRequest();
        Response res = request.post("https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN");
        System.out.println(new ObjectMapper().writeValueAsString(res));
        System.out.println(res.getData());

        Request request1 = new URLRequest();
        Map<String, Object> query = new HashMap<>();
        query.put("access_token", "ACCESS_TOKEN");
        Response res1 = request1.post("https://api.weixin.qq.com/cgi-bin/tags/create", query);
        System.out.println(new ObjectMapper().writeValueAsString(res1));
        System.out.println(res1.getData());

        Request request2 = new URLRequest();
        Map<String, Object> params = new HashMap<>();
        Map<String, String> child = new HashMap<>();
        child.put("name", "发文发问发我");
        params.put("tag", child);
        System.out.println("发我发我");
        System.out.println(new ObjectMapper().writeValueAsString(params));
        Response res2 = request2.post("https://api.weixin.qq.com/cgi-bin/tags/create", query, params);
        System.out.println(new ObjectMapper().writeValueAsString(res2));
        System.out.println(res2.getData());
    }
}
