package com.example.reduxserverspring.counters;

import com.example.reduxserverspring.counters.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/counters/{index}")
    public JsonCounter putCounter(@PathVariable("index") @Min(0) int index,
                                  @Valid @RequestBody PutCounterDto countData) {
        logger.info("setting counter with index {} to value {}", index, countData.count());
        final int value = countersService.setCounter(index, countData.count());
        return new JsonCounter(new CounterDto(new Counter(index, value)));
    }

    @PutMapping("/counters/{index}/decrement")
    public JsonCounter decrementCounter(@PathVariable("index") @Min(0) int index,
                                        @Valid @RequestBody DecIncCounterDto decData) {
        logger.info("decrementing counter with index {} by {}", index, decData.by());
        final int value = countersService.decrementByValue(index, decData.by());
        return new JsonCounter(new CounterDto(new Counter(index, value)));
    }

    @PutMapping("/counters/{index}/increment")
    public JsonCounter incrementCounter(@PathVariable("index") @Min(0) int index,
                                        @Valid @RequestBody DecIncCounterDto incData) {
        logger.info("incrementing counter with index {} by {}", index, incData.by());
        final int value = countersService.incrementByValue(index, incData.by());
        return new JsonCounter(new CounterDto(new Counter(index, value)));
    }
}
