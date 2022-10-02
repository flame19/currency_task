package com.currency.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@JsonIgnoreProperties
@Data
public class CurrencyDto {
    private String ccy;
    private Double buy;
    private Double sale;
    private LocalDate date;
}
