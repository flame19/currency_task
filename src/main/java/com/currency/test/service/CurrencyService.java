package com.currency.test.service;

import com.currency.test.dto.CurrencyDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CurrencyService {

    List<CurrencyDto> getCurrencies();
    List<String> getAvailableCodes();
    List<CurrencyDto> getCurrenciesByCcy(String ccy);
    List<CurrencyDto> getCurrenciesByDate(LocalDate date);
    Map<String, List<CurrencyDto>> getCurrenciesDateRange(String startDate, String endDate);
}
