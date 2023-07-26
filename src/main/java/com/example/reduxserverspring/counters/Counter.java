package com.example.reduxserverspring.counters;

import jakarta.validation.constraints.Min;

public record Counter(@Min(0) int index, int value) {
}
