package com.example.reduxserverspring.counters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountersController.class)
class CountersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountersService countersService;

    @Test
    void testGetCounters() throws Exception {
        given(this.countersService.getCounters()).willReturn(Arrays.asList(new Counter(0, 10), new Counter(1, 11)));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/counters"))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("okay"))
                .andExpect(jsonPath("$.data.counters").isArray())
                .andExpect(jsonPath("$.data.counters").isNotEmpty())
                .andExpect(jsonPath("$.data.counters[0].index").value(0))
                .andExpect(jsonPath("$.data.counters[0].value").value(10))
                .andExpect(jsonPath("$.data.counters[1].index").value(1))
                .andExpect(jsonPath("$.data.counters[1].value").value(11));
    }

    @Test
    void testGetCounter() throws Exception {
        given(this.countersService.getCounter(anyInt())).willReturn(10);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/counters/1"))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("okay"))
                .andExpect(jsonPath("$.data.counter.index").value(1))
                .andExpect(jsonPath("$.data.counter.value").value(10));
    }

    @Test
    void testGetCounterWithInvalidIndex() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/counters/-1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("must be greater than or equal to 0"));
    }

    @Test
    void testPutCounter() throws Exception {
        given(this.countersService.setCounter(anyInt(), anyInt())).willReturn(11);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/counters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"count\": 10 }")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("okay"))
                .andExpect(jsonPath("$.data.counter.index").value(1))
                .andExpect(jsonPath("$.data.counter.value").value(11));
    }

    @Test
    void testPutCounterWithInvalidIndex() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/counters/-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"count\": 10 }")

                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("must be greater than or equal to 0"));
    }

    @Test
    void testPutCounterWithInvalidCount() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/counters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"count\": -1 }")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("must be greater than or equal to 0"));
    }

    @Test
    void testDecrementCounter() throws Exception {
        given(this.countersService.decrementByValue(anyInt(), anyInt())).willReturn(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/counters/1/decrement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"by\": 1 }")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("okay"))
                .andExpect(jsonPath("$.data.counter.index").value(1))
                .andExpect(jsonPath("$.data.counter.value").value(1));
    }

    @Test
    void testDecrementCounterWithInvalidIndex() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/counters/-1/decrement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"by\": 10 }")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("must be greater than or equal to 0"));
    }

    @Test
    void testDecrementCounterWithInvalidCount() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/counters/1/decrement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"by\": -1 }")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("must be greater than or equal to 1"));
    }

    @Test
    void testIncrementCounter() throws Exception {
        given(this.countersService.incrementByValue(anyInt(), anyInt())).willReturn(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/counters/1/increment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"by\": 1 }")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("okay"))
                .andExpect(jsonPath("$.data.counter.index").value(1))
                .andExpect(jsonPath("$.data.counter.value").value(1));
    }

    @Test
    void testIncrementCounterWithInvalidIndex() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/counters/-1/increment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"by\": 10 }")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("must be greater than or equal to 0"));
    }

    @Test
    void testIncrementCounterWithInvalidCount() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/counters/1/increment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"by\": -1 }")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("must be greater than or equal to 1"));
    }
}
