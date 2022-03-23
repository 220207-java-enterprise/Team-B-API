package com.revature.ers.dtos.requests;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PrismRegisterRequest {
    @Value("ers")
    private String name;

    @Value("${prism.key}")
    private String key;

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "PrismRegisterRequest{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
