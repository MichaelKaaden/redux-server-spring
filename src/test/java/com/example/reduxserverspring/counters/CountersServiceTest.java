package com.example.reduxserverspring.counters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class CountersServiceTest {
    CountersService countersService;

    @BeforeEach
    void setUp() {
        countersService = new CountersService();
    }

    @Test
    void getCounter() {
        assertSame(0, countersService.getCounter(42));
    }

    @Test
    void increment() {
        assertSame(0, countersService.getCounter(42));
        countersService.increment(42);
        assertSame(1, countersService.getCounter(42));
        countersService.increment(42);
        assertSame(2, countersService.getCounter(42));
    }

    @Test
    void decrement() {
        assertSame(0, countersService.getCounter(42));
        countersService.decrement(42);
        assertSame(-1, countersService.getCounter(42));
        countersService.decrement(42);
        assertSame(-2, countersService.getCounter(42));
    }
}
