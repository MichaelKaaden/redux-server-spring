package com.example.reduxserverspring.counters.dto;

import org.springframework.http.HttpStatus;

public record JsonCounter(CounterDto data, int status, String message) {
    public JsonCounter(CounterDto data) {
        this(data, HttpStatus.OK.value(), "okay");
    }
}
