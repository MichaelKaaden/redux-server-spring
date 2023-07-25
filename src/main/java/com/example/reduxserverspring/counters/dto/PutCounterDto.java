package com.example.reduxserverspring.counters.dto;

import jakarta.validation.constraints.Min;

public record PutCounterDto(@Min(0) int count) {
}
