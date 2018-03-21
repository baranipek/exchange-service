package com.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class ExchangeRateDto implements Serializable {

    private static final long serialVersionUID = -2517003655094815427L;

    public ExchangeRateDto(String base, Date createdTime, String symbol, BigDecimal rate) {
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put(symbol, rate);

        this.base = base;
        this.rates = rates;
        this.date = createdTime.toString();
    }


    private String base;
    private String date;
    private Map<String, BigDecimal> rates;

}
