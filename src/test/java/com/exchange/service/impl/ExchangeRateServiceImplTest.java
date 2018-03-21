package com.exchange.service.impl;

import com.exchange.client.ExchangeRateClient;
import com.exchange.dto.ExchangeRateDto;
import com.exchange.entity.ExchangeChangeEntity;
import com.exchange.helper.DateHelper;
import com.exchange.mapper.ExchangeRateMapper;
import com.exchange.repository.ExchangeRateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateServiceImplTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @Mock
    private ExchangeRateRepository repository;

    @Spy
    private ExchangeRateMapper mapper;

    @Mock
    private DateHelper dateHelper;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    private ExchangeRateDto latestRateDto;

    private ExchangeChangeEntity latestEntity;

    private List<ExchangeChangeEntity> exchangeChangeEntities;

    private ExchangeChangeEntity firstEntity;

    @Before
    public void setUp() throws Exception {
        latestRateDto = new ExchangeRateDto("EUR", new Date(), "USD", new BigDecimal(1.2));
        latestEntity = ExchangeChangeEntity.builder().base("EUR").createdTime(new Date()).rate(new BigDecimal(1.2))
                .symbol("USD").build();

        firstEntity = ExchangeChangeEntity.builder().base("TL").createdTime(new Date()).rate(new BigDecimal(1.1))
                .symbol("USD").build();
    }

    @Test
    public void shouldCallApiSuccessfully() throws Exception {
        when(exchangeRateClient.getCurrencyClient("EUR","USD")).thenReturn(latestRateDto);
        ExchangeRateDto exchangeRateDto = exchangeRateService.callExchangeApi("EUR","USD");
        assertNotNull(exchangeRateDto);
        assertEquals(exchangeRateDto.getBase(), "EUR");
    }

    @Test
    public void shouldReturnLatestRatesWithoutDateSearchCriteria() throws Exception {
        exchangeChangeEntities = new ArrayList<>();
        exchangeChangeEntities.add(latestEntity);

        when(repository.findFirstByBaseAndSymbolOrderByCreatedTime(any(), any())).thenReturn(exchangeChangeEntities);
        List<ExchangeRateDto> exchangeRateDtos = exchangeRateService.getCurrency("EUR", "USD", null, null);

        assertNotNull(exchangeRateDtos);
        assertEquals(exchangeRateDtos.size(), 1);
        assertEquals(exchangeRateDtos.get(0).getBase(), "EUR");
        assertEquals(exchangeRateDtos.get(0).getRates().get("USD"), new BigDecimal(1.2));

    }


    @Test
    public void shouldReturnLatestRatesWithDateSearchCriteria() throws Exception {
        exchangeChangeEntities = new ArrayList<>();
        exchangeChangeEntities.add(latestEntity);
        exchangeChangeEntities.add(firstEntity);

        when(repository.findAllByCreatedTimeBetween(any(), any())).thenReturn(exchangeChangeEntities);
        List<ExchangeRateDto> exchangeRateDtos = exchangeRateService.getCurrency("EUR", "USD", new Date(), new Date());

        assertNotNull(exchangeRateDtos);
        assertEquals(exchangeRateDtos.size(), 2);
    }

}