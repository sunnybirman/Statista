package com.statista.code.api;

public class FoobarStringProcessor implements StringProcessor {

    public boolean appliesToValue(String value) {
        return value != null && value.startsWith("foobar:");
    }

    public String processValue(String value) {
        return value.replace("foobar:","acme:");
    }
}
