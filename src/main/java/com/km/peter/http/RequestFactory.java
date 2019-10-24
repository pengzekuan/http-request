package com.km.peter.http;

public class RequestFactory {

    public static Request instance(Class clz) {
        try {
            return (Request) clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            System.err.println(e);
        }

        return null;
    }
}
