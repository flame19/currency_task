package com.currency.test.repository;

import com.currency.test.entity.Currency;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrencyRepository {

    List<Currency> findByCcy(String ccy);
    List<Currency> findByDate(LocalDate date);
    List<Currency> findByDateRange(String startDate, String endDate);
}
