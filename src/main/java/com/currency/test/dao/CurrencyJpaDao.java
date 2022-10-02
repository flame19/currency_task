package com.currency.test.dao;

import com.currency.test.entity.Currency;

import com.currency.test.repository.CurrencyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyJpaDao extends JpaRepository<Currency, Integer>, CurrencyRepository {

    List<Currency> findByCcy(String ccy);
    List<Currency> findByDate(LocalDate date);

    @Query(value = "SELECT * FROM CURRENCY WHERE DATE > ?1 AND DATE < ?2 ORDER BY DATE",
            nativeQuery = true)
    List<Currency> findByDateRange(String startDate, String endDate);
}
