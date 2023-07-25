package com.example.reduxserverspring.counters.dto;

import com.example.reduxserverspring.counters.Counter;

import java.util.List;

public record CountersDto(List<Counter> counters) {
}
