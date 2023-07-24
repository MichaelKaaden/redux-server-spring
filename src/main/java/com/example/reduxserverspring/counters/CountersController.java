package com.example.reduxserverspring.counters;

import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CountersController {
    CountersService countersService;
    Logger logger = LoggerFactory.getLogger(CountersController.class);

    CountersController(CountersService countersService) {
        this.countersService = countersService;
    }

    @GetMapping("/counters/{index}")
    public Counter getCounter(@PathVariable("index") @Min(0) int index) {
        logger.info("index is {}", index);
        final int value = countersService.getCounter(index);
        return new Counter(index, value);
    }
}
