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
    void setCounter() {
        assertSame(0, countersService.getCounter(42));
        countersService.setCounter(42, 12);
        assertSame(12, countersService.getCounter(42));
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
    void incrementByValue() {
        assertSame(0, countersService.getCounter(42));
        countersService.incrementByValue(42, 3);
        assertSame(3, countersService.getCounter(42));
        countersService.incrementByValue(42, 3);
        assertSame(6, countersService.getCounter(42));
    }

    @Test
    void decrement() {
        assertSame(0, countersService.getCounter(42));
        countersService.decrement(42);
        assertSame(-1, countersService.getCounter(42));
        countersService.decrement(42);
        assertSame(-2, countersService.getCounter(42));
    }

    @Test
    void decrementByValue() {
        assertSame(0, countersService.getCounter(42));
        countersService.decrementByValue(42, 3);
        assertSame(-3, countersService.getCounter(42));
        countersService.decrementByValue(42, 3);
        assertSame(-6, countersService.getCounter(42));
    }
}
