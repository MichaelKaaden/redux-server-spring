package com.example.reduxserverspring.counters;

import com.example.reduxserverspring.counters.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
    @Operation(summary = "Get all currently defined counters.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(
                                            summary = "An array containing two counters.",
                                            value = """
                                                    {
                                                      "data": {
                                                        "counters": [
                                                          {
                                                            "index": 0,
                                                            "value": 42
                                                          },
                                                          {
                                                            "index": 1,
                                                            "value": 4711
                                                          }
                                                        ]
                                                      },
                                                      "message": "okay",
                                                      "status": 200
                                                    }
                                                    """)
                            },
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = JsonCounters.class)))
            })
    @ApiResponse(responseCode = "200", description = "An array containing all counters.")
    public JsonCounters getCounters() {
        logger.info("getting all counters");
        return new JsonCounters(new CountersDto(countersService.getCounters()));
    }

    @GetMapping("/counters/{index}")
    @Operation(summary = "Get a specific counter.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(
                                            summary = "The requested counter.",
                                            value = """
                                                    {
                                                       "data": {
                                                         "counter": {
                                                           "index": 0,
                                                           "value": 42
                                                         }
                                                       },
                                                       "message": "okay",
                                                       "status": 200
                                                     }
                                                    """)
                            },
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = JsonCounter.class)))
            })
    @ApiResponse(responseCode = "200", description = "The requested counter.")
    public JsonCounter getCounter(@PathVariable("index") @Min(0) int index) {
        logger.info("getting counter with index {}", index);
        final int value = countersService.getCounter(index);
        return new JsonCounter(new CounterDto(new Counter(index, value)));
    }

    @PutMapping("/counters/{index}")
    @Operation(summary = "Set a specific counter. Use with great care in a distributed environment!",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(
                                            summary = "The updated counter.",
                                            value = """
                                                    {
                                                       "data": {
                                                         "counter": {
                                                           "index": 0,
                                                           "value": 42
                                                         }
                                                       },
                                                       "message": "okay",
                                                       "status": 200
                                                     }
                                                    """)
                            },
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = JsonCounter.class)))
            })
    @ApiResponse(responseCode = "200", description = "The updated counter.")
    public JsonCounter putCounter(@PathVariable("index") @Min(0) int index,
                                  @Valid @RequestBody PutCounterDto countData) {
        logger.info("setting counter with index {} to value {}", index, countData.count());
        final int value = countersService.setCounter(index, countData.count());
        return new JsonCounter(new CounterDto(new Counter(index, value)));
    }

    @PutMapping("/counters/{index}/decrement")
    @Operation(summary = "Decrement a counter.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(
                                            summary = "The updated counter.",
                                            value = """
                                                    {
                                                       "data": {
                                                         "counter": {
                                                           "index": 0,
                                                           "value": 42
                                                         }
                                                       },
                                                       "message": "okay",
                                                       "status": 200
                                                     }
                                                    """)
                            },
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = JsonCounter.class)))
            })
    @ApiResponse(responseCode = "200", description = "The updated counter.")
    public JsonCounter decrementCounter(@PathVariable("index") @Min(0) int index,
                                        @Valid @RequestBody DecIncCounterDto decData) {
        logger.info("decrementing counter with index {} by {}", index, decData.by());
        final int value = countersService.decrementByValue(index, decData.by());
        return new JsonCounter(new CounterDto(new Counter(index, value)));
    }

    @PutMapping("/counters/{index}/increment")
    @Operation(summary = "Increment a counter.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(
                                            summary = "The updated counter.",
                                            value = """
                                                    {
                                                       "data": {
                                                         "counter": {
                                                           "index": 0,
                                                           "value": 42
                                                         }
                                                       },
                                                       "message": "okay",
                                                       "status": 200
                                                     }
                                                    """)
                            },
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = JsonCounter.class)))
            })
    @ApiResponse(responseCode = "200", description = "The updated counter.")
    public JsonCounter incrementCounter(@PathVariable("index") @Min(0) int index,
                                        @Valid @RequestBody DecIncCounterDto incData) {
        logger.info("incrementing counter with index {} by {}", index, incData.by());
        final int value = countersService.incrementByValue(index, incData.by());
        return new JsonCounter(new CounterDto(new Counter(index, value)));
    }
}
