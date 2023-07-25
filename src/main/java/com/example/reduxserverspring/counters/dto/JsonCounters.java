package com.example.reduxserverspring.counters.dto;

import org.springframework.http.HttpStatus;

public record JsonCounters(CountersDto data, int status, String message) {
    public JsonCounters(CountersDto data) {
        this(data, HttpStatus.OK.value(), "okay");
    }
}
