package com.km.peter.http;

import java.nio.charset.StandardCharsets;

public class Response {

    private String contentType;

    private int code;

    private String message;

    private byte[] bytes;
    private String data;

    public Response() {
    }

    public Response(int code, String message, String data) {
        this.code = code;
        this.message = message;
    }

    public String getData() {

        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {

        this.bytes = bytes;
        this.data = new String(this.bytes, StandardCharsets.UTF_8);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
