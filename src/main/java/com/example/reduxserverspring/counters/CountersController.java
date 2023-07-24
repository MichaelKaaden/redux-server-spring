package com.example.reduxserverspring.counters;

import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class CountersController {
    CountersService countersService;
    Logger logger = LoggerFactory.getLogger(CountersController.class);

    CountersController(CountersService countersService) {
        this.countersService = countersService;
    }

    @GetMapping("/counters")
    public List<Counter> getCounters() {
        logger.info("getting all counters");
        return countersService.getCounters();
    }

    @GetMapping("/counters/{index}")
    public Counter getCounter(@PathVariable("index") @Min(0) int index) {
        logger.info("getting counter with index {}", index);
        final int value = countersService.getCounter(index);
        return new Counter(index, value);
    }

    @PutMapping("/counters/{index}/decrement")
    public Counter decrementCounter(@PathVariable("index") @Min(0) int index) {
        logger.info("decrementing counter with index {}", index);
        final int value = countersService.decrement(index);
        return new Counter(index, value);
    }

    @PutMapping("/counters/{index}/increment")
    public Counter incrementCounter(@PathVariable("index") @Min(0) int index) {
        logger.info("incrementing counter with index {}", index);
        final int value = countersService.increment(index);
        return new Counter(index, value);
    }
}
