package com.km.peter.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class URLRequest extends CommonRequest {

    public <T> Response request(Class<T> clz, URL url, String method, Object params) {

        T connection = null;

        InputStream inputStream = null;

        ByteArrayOutputStream outputStream = null;

        OutputStreamWriter writer = null;

        OutputStream out = null;

        try {
            connection = clz.cast(url.openConnection());

            // 设置请求方法
            clz.getMethod("setRequestMethod", String.class).invoke(connection, method);

            // 设置超时
            clz.getMethod("setConnectTimeout", int.class).invoke(connection, URLRequest.CONNECTION_TIMEOUT);
            clz.getMethod("setReadTimeout", int.class).invoke(connection, URLRequest.READ_TIMEOUT);

            // 设置请求头
//            clz.getMethod("setRequestProperty", String.class, String.class).invoke(connection, "Content-Type", URLRequest.CONTENT_TYPE.getValue());

            // 请求参数
            clz.getMethod("setDoInput", boolean.class).invoke(connection, true);
            clz.getMethod("setDoOutput", boolean.class).invoke(connection, true);

            clz.getMethod("setUseCaches", boolean.class).invoke(connection, false);

            // 连接资源
            clz.getMethod("connect").invoke(connection);

            // 传递参数
            if (params != null) {
                out = (OutputStream) clz.getMethod("getOutputStream").invoke(connection);
                writer = new OutputStreamWriter(out);
                writer.write(new ObjectMapper().writeValueAsString(params));
                writer.flush();
            }

            Response response = new Response();

            int responseCode = (int) clz.getMethod("getResponseCode").invoke(connection);

            response.setCode(responseCode);
            response.setMessage((String) clz.getMethod("getResponseMessage").invoke(connection));

            if (responseCode == HttpURLConnection.HTTP_OK) {
                int length = (int) clz.getMethod("getContentLength").invoke(connection);
                System.out.println("contentLength:" + length);

                String contentType = (String) clz.getMethod("getContentType").invoke(connection);
                System.out.println("contentType:" + contentType);

                response.setContentType(contentType);

                if (length == 0) {
                    return response;
                }

                inputStream = (InputStream) clz.getMethod("getInputStream").invoke(connection);

                outputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];

                int len;

                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }

                response.setBytes(outputStream.toByteArray());

                return response;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {

                    clz.getMethod("disconnect").invoke(connection);
                }

                Objects.requireNonNull(inputStream).close();
                Objects.requireNonNull(outputStream).close();

                if (writer != null) {
                    writer.close();
                }

                if (out != null) {
                    out.close();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Response httpRequest(URL url, String method, Object params) {

        return this.request(HttpURLConnection.class, url, method, params);
    }

    public Response httpsRequest(URL url, String method, Object params) {

        return this.request(HttpsURLConnection.class, url, method, params);
    }

    @Override
    public Response request(String uri, String method, Map<String, Object> query, Object params) {
        try {
            if (query != null && query.size() > 0) {
                uri += "?" + queryBuild(query);
            }
            URL url = new URL(uri);
            System.out.println("query:" + url.getQuery());
            String protocol = url.getProtocol();
            String methodName = protocol + "Request";
            return (Response) this.getClass().getMethod(methodName, URL.class, String.class, Object.class)
                    .invoke(this.getClass().newInstance(), url, method, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
