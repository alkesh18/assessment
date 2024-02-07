package org.example.model.airport;

import org.example.model.BaseModel;

public interface Airport extends BaseModel {
    String getCode();
    void setCode(String code);
    String getName();
    void setName(String name);
}
