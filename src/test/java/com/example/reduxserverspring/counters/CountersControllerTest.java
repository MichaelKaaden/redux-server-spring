package com.example.reduxserverspring.counters;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        given(this.countersService.getCounter(1)).willReturn(10);
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
        assertThrows(ServletException.class, () -> {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/counters/-1"));
        });

    }

    @Test
    void testPutCounter() {
    }

    @Test
    void testDecrementCounter() {
    }

    @Test
    void testIncrementCounter() {
    }
}
