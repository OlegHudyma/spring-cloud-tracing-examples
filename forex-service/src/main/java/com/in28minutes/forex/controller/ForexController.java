package com.in28minutes.forex.controller;

import com.in28minutes.forex.entity.ExchangeValue;
import com.in28minutes.forex.repository.ExchangeValueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ForexController {

  private final Environment environment;

  private final ExchangeValueRepository repository;

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
    log.info("Getting exchange value from repository. From: {}, To: {}", from, to);

    ExchangeValue exchangeValue =
            repository.findByFromAndTo(from, to);

    exchangeValue.setPort(
            Integer.parseInt(environment.getProperty("local.server.port")));

    return exchangeValue;
  }
}