package com.km.peter.http;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class URLRequest extends CommonRequest {

    public <T> Response request(Class<T> clz, URL url, String method) {

        T connection = null;

        InputStream inputStream = null;

        ByteArrayOutputStream outputStream = null;

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
                assert connection != null;
                clz.getMethod("disconnect").invoke(connection);

                assert inputStream != null;
                inputStream.close();
                assert outputStream != null;
                outputStream.close();
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

    public Response httpRequest(URL url, String method) {

        return this.request(HttpURLConnection.class, url, method);
    }

    public Response httpsRequest(URL url, String method) {

        return this.request(HttpsURLConnection.class, url, method);
    }

    @Override
    public Response request(String uri, String method, Map<String, Object> query) {

        try {
            if (query != null && query.size() > 0) {
                uri += "?" + queryBuild(query);
            }
            URL url = new URL(uri);
            System.out.println("query:" + url.getQuery());
            String protocol = url.getProtocol();
            String methodName = protocol + "Request";
            return (Response) this.getClass().getMethod(methodName, URL.class, String.class)
                    .invoke(this.getClass().newInstance(), url, method);
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
