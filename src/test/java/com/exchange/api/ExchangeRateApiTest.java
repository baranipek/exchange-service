package com.exchange.api;

import com.exchange.dto.ExchangeRateDto;
import com.exchange.service.impl.ExchangeServiceImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.LOG_DEBUG)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExchangeRateApiTest {

    @Autowired
    private MockMvc mockModelView;

    @Mock
    private ExchangeServiceImpl exchangeRateService;

    private ExchangeRateDto latestRateDto;

    @Before
    public void setUp() throws Exception {
        latestRateDto = new ExchangeRateDto("EUR", new Date(), "USD", new BigDecimal(1.2));
    }

    @Test
    public void getExchangeRatesSuccessFully() throws Exception {
        List<ExchangeRateDto> rateDtoList = new ArrayList<>();
        rateDtoList.add(latestRateDto);

        when(exchangeRateService.getCurrency(any(), any(),any(),any())).thenReturn(rateDtoList);
        mockModelView.perform(get("/api/v1/exchange/"+"EUR" + "/" + "USD").contentType(APPLICATION_JSON_UTF8)).
                andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

    }

}