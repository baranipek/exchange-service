package com.exchange.client;


import com.exchange.dto.ExchangeRateDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exhangeRate", url = "http://api.fixer.io/latest" ,fallbackFactory = ExchangeFallBackFactory.class)
@Component
public interface ExchangeClient {

    @RequestMapping(method = RequestMethod.GET)
    ExchangeRateDto getCurrencyClient(@RequestParam("base") String base, @RequestParam("symbols") String symbols);
}
