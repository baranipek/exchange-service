package com.exchange.api;

import com.exchange.dto.DateSearchDto;
import com.exchange.dto.ExchangeRateDto;
import com.exchange.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/exchange")
public class ExchangeRateApi {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateApi(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @RequestMapping(value = "/{base}/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRates(@PathVariable("base") String base,
                                                                  @PathVariable("symbol") String symbol,
                                                                  DateSearchDto searchDto) {
        return new ResponseEntity<>(exchangeRateService.getCurrency(base, symbol, searchDto.getStartDate(), searchDto.getEndDate()), HttpStatus.OK);
    }


}
