package com.example.reduxserverspring.counters.dto;

import jakarta.validation.constraints.Min;

public record DecIncCounterDto(@Min(1) int by) {
}
