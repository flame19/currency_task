package com.currency.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CurrencyTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void currencyTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/currency/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("BTC")))
                .andExpect(content().string(containsString("EUR")));
    }

    @Test
    public void codesTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/currency/codes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("USD")))
                .andExpect(content().string(containsString("EUR")))
                .andExpect(content().string(containsString("BTC")));
    }

    @Test
    public void historicalByCcyTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/currency/getHistory/byCcy")
                        .param("ccy", "usd"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("usd")));
    }

    @Test
    public void historicalByDateTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/currency/getHistory/byDate")
                        .param("date", "2022-10-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("2022-10-01")));
    }

    @Test
    public void historicalByDateRangeTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/currency/getHistory/dateRange")
                        .param("startDate", "2022-10-02")
                        .param("endDate", "2022-10-04"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("2022-10-03")));
    }
}
