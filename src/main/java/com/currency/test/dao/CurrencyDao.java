package com.currency.test.dao;

import com.currency.test.entity.Currency;
import com.currency.test.repository.CurrencyRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CurrencyDao {

    private CurrencyRepository currencyJpaDao;

    public CurrencyDao(CurrencyRepository currencyJpaDao) {
        this.currencyJpaDao = currencyJpaDao;
    }

    public List<Currency> findByCcy(String ccy) {
        return currencyJpaDao.findByCcy(ccy);
    }

    public List<Currency> findByDate(LocalDate date) {
        return currencyJpaDao.findByDate(date);
    }

    public List<Currency> findByDateRange(String startDate, String endDate) {
        return currencyJpaDao.findByDateRange(startDate, endDate);
    }
}
