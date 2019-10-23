package com.km.peter.http.enums;

public enum ContentType {
    JSON("json", "application/json"),
    XML("xml", "application/xml"),
    HTML("html", "text/html");

    private String key;

    private String value;

    ContentType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value + ";charset=utf-8";
    }

    public void setValue(String value) {
        this.value = value;
    }
}
