package com.km.peter.http;

public class RequestFactory {

    public static Request instance(Class clz) throws IllegalAccessException, InstantiationException {
        return (Request) clz.newInstance();
    }
}
