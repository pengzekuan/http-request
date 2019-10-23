package com.km.peter.http;

import java.nio.charset.StandardCharsets;

public class Response {

    private String contentType;

    private int code;

    private String message;

    private byte[] bytes;

    public Response() {
    }

    public Response(int code, String message, String data) {
        this.code = code;
        this.message = message;
    }

    public String getData() {

        return new String(this.bytes, StandardCharsets.UTF_8);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
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
