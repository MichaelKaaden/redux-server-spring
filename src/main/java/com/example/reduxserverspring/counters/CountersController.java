package com.example.reduxserverspring.counters;

import com.example.reduxserverspring.counters.dto.CounterDto;
import com.example.reduxserverspring.counters.dto.CountersDto;
import com.example.reduxserverspring.counters.dto.JsonCounter;
import com.example.reduxserverspring.counters.dto.JsonCounters;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CountersController {
    CountersService countersService;
    Logger logger = LoggerFactory.getLogger(CountersController.class);

    CountersController(CountersService countersService) {
        this.countersService = countersService;
    }

    @GetMapping("/counters")
    public JsonCounters getCounters() {
        logger.info("getting all counters");
        return new JsonCounters(new CountersDto(countersService.getCounters()));
    }

    @GetMapping("/counters/{index}")
    public JsonCounter getCounter(@PathVariable("index") @Min(0) int index) {
        logger.info("getting counter with index {}", index);
        final int value = countersService.getCounter(index);
        return new JsonCounter(new CounterDto(new Counter(index, value)));
    }

    @PutMapping("/counters/{index}/decrement")
    public JsonCounter decrementCounter(@PathVariable("index") @Min(0) int index) {
        logger.info("decrementing counter with index {}", index);
        final int value = countersService.decrement(index);
        return new JsonCounter(new CounterDto(new Counter(index, value)));
    }

    @PutMapping("/counters/{index}/increment")
    public JsonCounter incrementCounter(@PathVariable("index") @Min(0) int index) {
        logger.info("incrementing counter with index {}", index);
        final int value = countersService.increment(index);
        return new JsonCounter(new CounterDto(new Counter(index, value)));
    }
}
