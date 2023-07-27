package com.example.reduxserverspring.counters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static org.junit.jupiter.api.Assertions.*;

class CountersServiceTest {
    CountersService countersService;

    @BeforeEach
    void setUp() {
        countersService = new CountersService();
    }

    @Test
    void testGetCounter() {
        assertSame(0, countersService.getCounter(42));
    }

    @Test
    void testGetCounters() {
        countersService.increment(42);
        ArrayList<Counter> expected = new ArrayList<>(List.of(new Counter(42, 1)));
        assertEquals(expected, countersService.getCounters());
    }

    @Test
    void testSetCounter() {
        assertSame(0, countersService.getCounter(42));
        countersService.setCounter(42, 12);
        assertSame(12, countersService.getCounter(42));
    }

    @Test
    void testIncrement() {
        assertSame(0, countersService.getCounter(42));
        countersService.increment(42);
        assertSame(1, countersService.getCounter(42));
        countersService.increment(42);
        assertSame(2, countersService.getCounter(42));
    }

    @Test
    void testIncrementByValue() {
        assertSame(0, countersService.getCounter(42));
        countersService.incrementByValue(42, 3);
        assertSame(3, countersService.getCounter(42));
        countersService.incrementByValue(42, 3);
        assertSame(6, countersService.getCounter(42));
    }

    @Test
    void testDecrement() {
        assertSame(0, countersService.getCounter(42));
        countersService.decrement(42);
        assertSame(-1, countersService.getCounter(42));
        countersService.decrement(42);
        assertSame(-2, countersService.getCounter(42));
    }

    @Test
    void testDecrementByValue() {
        assertSame(0, countersService.getCounter(42));
        countersService.decrementByValue(42, 3);
        assertSame(-3, countersService.getCounter(42));
        countersService.decrementByValue(42, 3);
        assertSame(-6, countersService.getCounter(42));
    }

    @Test
    void testConcurrentAccessToTheSameCounter() {
        int theCounterEveryoneWorksOn = 42;
        int threadNum = 5;
        int count = 0;
        int maxRuns = 1_000;

        try {
            // Prepare to execute and store the Futures
            ExecutorService executor = Executors.newFixedThreadPool(threadNum);
            List<FutureTask<Integer>> taskList = new ArrayList<>();

            // Start threads
            for (int i = 0; i < threadNum; i++) {
                FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        for (int j = 0; j < maxRuns; j++) {
                            countersService.decrement(theCounterEveryoneWorksOn);
                            countersService.increment(theCounterEveryoneWorksOn);
                        }

                        countersService.increment(theCounterEveryoneWorksOn);
                        return countersService.getCounter(theCounterEveryoneWorksOn);
                    }
                });
                taskList.add(task);
                executor.execute(task);
            }

            for (int i = 0; i < threadNum; i++) {
                FutureTask<Integer> futureTask = taskList.get(i);
                count += futureTask.get();
            }
            executor.shutdown();
        } catch (Exception e) {
            fail(e);
        }

        assertSame(threadNum, countersService.getCounter(theCounterEveryoneWorksOn));
    }
}
