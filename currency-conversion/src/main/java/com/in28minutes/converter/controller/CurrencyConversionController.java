package com.in28minutes.converter.controller;

import com.in28minutes.converter.client.CurrencyExchangeServiceProxy;
import com.in28minutes.converter.dto.CurrencyConversionBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

  private final CurrencyExchangeServiceProxy proxy;

  @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
                                                     @PathVariable BigDecimal quantity) {
    CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

    log.info("Getting conversion results with feign client.");

    return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
            quantity.multiply(response.getConversionMultiple()), response.getPort());
  }
}