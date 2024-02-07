package org.example.model.parser;

import org.example.model.BaseModel;

import java.util.Map;

public interface ModelParser<T> extends BaseModel {

    T parseFromJson(String filepath) throws Exception;
    T parseFromConsoleInput(Map<String, String> input) throws Exception;
}
