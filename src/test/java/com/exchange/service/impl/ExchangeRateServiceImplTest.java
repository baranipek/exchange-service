package com.exchange.service.impl;

import com.exchange.client.ExchangeClient;
import com.exchange.dto.ExchangeRateDto;
import com.exchange.entity.ExchangeEntity;
import com.exchange.helper.DateHelper;
import com.exchange.mapper.ExchangeMapper;
import com.exchange.repository.ExchangeRepository;
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
    private ExchangeClient exchangeRateClient;

    @Mock
    private ExchangeRepository repository;

    @Spy
    private ExchangeMapper mapper;

    @Mock
    private DateHelper dateHelper;

    @InjectMocks
    private ExchangeServiceImpl exchangeRateService;

    private ExchangeRateDto latestRateDto;

    private ExchangeEntity latestEntity;

    private List<ExchangeEntity> exchangeChangeEntities;

    private ExchangeEntity firstEntity;

    @Before
    public void setUp() throws Exception {
        latestRateDto = new ExchangeRateDto("EUR", new Date(), "USD", new BigDecimal(1.2));
        latestEntity = ExchangeEntity.builder().base("EUR").createdTime(new Date()).rate(new BigDecimal(1.2))
                .symbol("USD").build();

        firstEntity = ExchangeEntity.builder().base("TL").createdTime(new Date()).rate(new BigDecimal(1.1))
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