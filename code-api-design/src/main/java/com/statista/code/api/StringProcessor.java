package com.statista.code.api;

public interface StringProcessor {
    boolean appliesToValue(String value);
    String processValue(String value);
}
