package com.example.util;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author Sourire
 */
public class AJAXRespnose extends HashMap<String,Object> {
    private static final long serialVersionUID = -8713837118340960775L;

    public AJAXRespnose code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public AJAXRespnose message(String message) {
        this.put("msg", message);
        return this;
    }

    public AJAXRespnose data(Object data) {
        this.put("data", data);
        return this;
    }

    public AJAXRespnose success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public AJAXRespnose fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public AJAXRespnose put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
