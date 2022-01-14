package com.ubs.forex.validations.common;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;

@Service
public class DateTimeService {

    public LocalDate getCurrentDate() {
        return LocalDate.of(2020, Month.OCTOBER, 10);
    }
}
