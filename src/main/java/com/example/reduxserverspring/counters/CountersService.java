package com.example.reduxserverspring.counters;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A little service to manage counters implemented with a concurrent hash map
 * that has the counter's index as key and it's value as value.
 * The counters may be read or written concurrently.
 */
@Service
public class CountersService {
    private final ConcurrentHashMap<Integer, AtomicInteger> countersMap;

    /**
     * Constructor
     */
    CountersService() {
        this.countersMap = new ConcurrentHashMap<>();
    }

    /**
     * Retrieve the given counter's value.
     *
     * @param index The counter's index
     * @return The counter's value
     */
    public int getCounter(int index) {
        return getOrInitializeValue(index).get();
    }

    /**
     * Retrieve the given counter's value. If it doesn't exist yet,
     * it's atomically created first.
     *
     * @param index The counter's index
     * @return The counter's value
     */
    private AtomicInteger getOrInitializeValue(int index) {
        countersMap.putIfAbsent(index, new AtomicInteger(0));
        return countersMap.get(index);
    }

    /**
     * Increment the given counter's value. If it doesn't exist yet,
     * it's atomically created first.
     *
     * @param index The counter's index
     * @return The counter's value
     */
    public int increment(int index) {
        return getOrInitializeValue(index).incrementAndGet();
    }

    /**
     * Decrement the given counter's value. If it doesn't exist yet,
     * it's atomically created first.
     *
     * @param index The counter's index
     * @return The counter's value
     */
    public int decrement(int index) {
        return getOrInitializeValue(index).decrementAndGet();
    }
}
