package com.bo.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Supplier;

public class JsonUtil {


    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String toJson(Object waitToJson) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(waitToJson);
        } catch (JsonProcessingException e) {
        }
        return json;
    }

    public static <T> T jsonToObject(String json, Class<T> clazz, Supplier<T> defaultValue) {
        T t = null;
        try {
            t = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            t = defaultValue.get();
        }
        return t;
    }

    public static <T> T jsonToObject(String json, Class<T> clazz, T defaultValue) {
        T t = null;
        try {
            t = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            t = defaultValue;
        }
        return t;
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> T jsonToObject(String json, TypeReference<T> typeReference, Supplier<T> defaultValue) {
        T t = null;
        try {
            t = objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            t = defaultValue.get();
        }
        return t;
    }

    public static <T> T jsonToObject(String json, TypeReference<T> typeReference, T defaultValue) {
        T t = null;
        try {
            t = objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            t = defaultValue;
        }
        return t;
    }

    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) throws JsonProcessingException {
        return objectMapper.readValue(json, typeReference);
    }

    public static JsonNode jsonToTree(String json) throws JsonProcessingException {
        return objectMapper.readTree(json);
    }
}
