package com.currency.test.service;

import com.currency.test.dao.CurrencyDao;
import com.currency.test.repository.CurrencyRepository;
import com.currency.test.dto.CurrencyDto;
import com.currency.test.entity.Currency;
import com.currency.test.service.impl.CurrencyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CurrencyServiceTest {

    private static CurrencyDto[] currencyDtos;
    private static Currency currency;

    private static final Double saleRate = 40.0;
    private static final Double buyRate = 39.0;

    private CurrencyService testObject;

    @BeforeAll
    public static void initCurrencyDtos() {
        String[] ccys = {"USD", "BTC", "EUR"};
        currencyDtos = new CurrencyDto[3];
        for(int i = 0; i < 3; i++) {
            currencyDtos[i] = new CurrencyDto();
            currencyDtos[i].setCcy(ccys[i]);
        }
        currency = new Currency();
        currency.setCcy("USD");
        currency.setBuy(buyRate);
        currency.setSale(saleRate);
        currency.setDate(LocalDate.now());
    }

    @BeforeEach
    public void init() {
        CurrencyRepository currencyRepositoryMock = Mockito.mock(CurrencyRepository.class);
        Mockito.when(currencyRepositoryMock.findByCcy(Mockito.anyString()))
                .thenReturn(List.of(currency));
        Mockito.when(currencyRepositoryMock.findByDate(LocalDate.now()))
                .thenReturn(List.of(currency));
        Mockito.when(currencyRepositoryMock.findByDateRange(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(List.of(currency));
        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        Mockito.when(restTemplateMock.getForObject(Mockito.anyString(), Mockito.any()))
                .thenReturn(currencyDtos);
        CurrencyDao currencyDaoMock = Mockito.mock(CurrencyDao.class);
        Mockito.when(currencyDaoMock.findByDateRange(Mockito.any(), Mockito.any()))
                .thenReturn(List.of(currency));
        Mockito.when(currencyDaoMock.findByDate(Mockito.any()))
                .thenReturn(List.of(currency));
        Mockito.when(currencyDaoMock.findByCcy(Mockito.anyString()))
                .thenReturn(List.of(currency));
        testObject = new CurrencyServiceImpl(currencyDaoMock, restTemplateMock);
    }

    @Test
    public void getCurrenciesTest() {
        List<CurrencyDto> list = testObject.getCurrencies();
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(LocalDate.now(), list.get(0).getDate());
        Assertions.assertEquals(LocalDate.now(), list.get(1).getDate());
        Assertions.assertEquals(LocalDate.now(), list.get(2).getDate());
    }

    @Test
    public void getAvailableCodesTest() {
        List<String> list = testObject.getAvailableCodes();
        Assertions.assertTrue(list.containsAll(List.of("USD", "EUR", "BTC")));
    }

    @Test
    public void getCurrenciesByCcyTest() {
        List<CurrencyDto> list = testObject.getCurrenciesByCcy("USD");
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(LocalDate.now(), list.get(0).getDate());
        Assertions.assertEquals("USD", list.get(0).getCcy());
        Assertions.assertEquals(saleRate, list.get(0).getSale());
        Assertions.assertEquals(buyRate, list.get(0).getBuy());
    }

    @Test
    public void getCurrenciesByDateTest() {
        List<CurrencyDto> list = testObject.getCurrenciesByDate(LocalDate.now());
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(LocalDate.now(), list.get(0).getDate());
        Assertions.assertEquals("USD", list.get(0).getCcy());
        Assertions.assertEquals(saleRate, list.get(0).getSale());
        Assertions.assertEquals(buyRate, list.get(0).getBuy());
    }

    @Test
    public void getCurrenciesDateRange() {
        Map<String, List<CurrencyDto>> map =
                testObject.getCurrenciesDateRange(LocalDate.now().toString(), LocalDate.now().toString());
        List<CurrencyDto> list = map.get(LocalDate.now().toString());
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(LocalDate.now(), list.get(0).getDate());
        Assertions.assertEquals("USD", list.get(0).getCcy());
        Assertions.assertEquals(saleRate, list.get(0).getSale());
        Assertions.assertEquals(buyRate, list.get(0).getBuy());
    }
}
