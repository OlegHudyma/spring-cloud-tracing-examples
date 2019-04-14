package com.in28minutes.converter.controller;

import com.google.common.collect.ImmutableMap;
import com.in28minutes.converter.client.CurrencyExchangeServiceProxy;
import com.in28minutes.converter.dto.CurrencyConversionBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

  private final CurrencyExchangeServiceProxy proxy;

  @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversionBean convertCurrency(@PathVariable String from,
                                                @PathVariable String to,
                                                @PathVariable BigDecimal quantity) {
    Map<String, String> uriVariables = ImmutableMap
            .<String, String>builder()
            .put("from", from)
            .put("to", to)
            .build();

    log.info("Getting conversion results with rest template.");

    ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
            "http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
            uriVariables);

    CurrencyConversionBean response = responseEntity.getBody();

    return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
            quantity.multiply(response.getConversionMultiple()), response.getPort());
  }

  @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
                                                     @PathVariable BigDecimal quantity) {
    CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

    log.info("Getting conversion results with feign client.");

    return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
            quantity.multiply(response.getConversionMultiple()), response.getPort());
  }
}