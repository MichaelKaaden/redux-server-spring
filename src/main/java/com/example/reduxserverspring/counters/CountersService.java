package com.example.reduxserverspring.counters;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<Counter> getCounters() {
        List<Counter> result = new ArrayList<>();
        countersMap.forEach((key, value) -> result.add(new Counter(key, value.get())));
        return result;
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
     * Increment the given counter's value. If it doesn't exist yet,
     * it's atomically created first.
     *
     * @param index The counter's index
     * @return The counter's value
     */
    public int increment(int index) {
        return incrementByValue(index, 1);
    }

    public int incrementByValue(int index, int value) {
        return getOrInitializeValue(index).addAndGet(value);
    }

    /**
     * Decrement the given counter's value. If it doesn't exist yet,
     * it's atomically created first.
     *
     * @param index The counter's index
     * @return The counter's value
     */
    public int decrement(int index) {
        return decrementByValue(index, 1);
    }

    public int decrementByValue(int index, int value) {
        return getOrInitializeValue(index).addAndGet(-value);
    }
}
