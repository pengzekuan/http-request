package com.km.peter.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HTTPClientRequest extends CommonRequest {

    @Override
    public Response request(String uri, String method, Map<String, Object> query, Object params, Map<String, String> headers) {

        if (query != null && query.size() > 0) {
            uri += "?" + HTTPClientRequest.queryBuild(query);
        }

        String methodName = "http" + method.substring(0, 1).toUpperCase() + method.substring(1).toLowerCase();
        try {
            return (Response) this.getClass()
                    .getDeclaredMethod(methodName, String.class, Object.class, Map.class)
                    .invoke(this.getClass().newInstance(), uri, params, headers);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println(e);
        }

        return null;
    }

    private Response execute(HttpRequestBase httpRequestMethod) {

        System.out.println("requestMethod:" + httpRequestMethod.getMethod());
        System.out.println("requestURI:" + httpRequestMethod.getURI());

        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        ResponseHandler<Response> responseHandler = httpResponse -> {
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            Response response = new Response();
            response.setCode(statusCode);
            response.setMessage(httpResponse.getStatusLine().getReasonPhrase());
            response.setContentType(String.valueOf(httpResponse.getLastHeader("Content-Type")));

            if (statusCode >= 200 && statusCode < 300) {
                HttpEntity entity = httpResponse.getEntity();
                InputStream inputStream = entity.getContent();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];

                int len;

                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }

                response.setBytes(outputStream.toByteArray());
            }
            return response;
        };

        try {
            return httpClient.execute(httpRequestMethod, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private <T> T setHeader(T requestMethod, Map<String, String> headers) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (headers == null || headers.size() == 0) {
            return requestMethod;
        }

        Method method = requestMethod.getClass().getMethod("setHeader", String.class, String.class);

        for (String key : headers.keySet()) {
            method.invoke(requestMethod, key, headers.get(key));
        }

        return requestMethod;
    }

    public Response httpGet(String url, Object params, Map<String, String> headers) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        HttpGet httpGet = new HttpGet(url);

        // set headers
        httpGet = this.setHeader(httpGet, headers);

        return this.execute(httpGet);
    }

    public Response httpPost(String url, Object params, Map<String, String> headers) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, JsonProcessingException {
        HttpPost httpPost = new HttpPost(url);

        // set headers
        httpPost = this.setHeader(httpPost, headers);

        // request with params
        httpPost.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(params), StandardCharsets.UTF_8));

        return this.execute(httpPost);
    }

}
