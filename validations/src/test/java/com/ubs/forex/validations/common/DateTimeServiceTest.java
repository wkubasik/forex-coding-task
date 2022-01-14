package com.ubs.forex.validations.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DateTimeServiceTest {

    private final DateTimeService dateTimeService = new DateTimeService();

    @Test
    void shouldGetCorrectDate() {
        // given && when
        LocalDate currentDate = dateTimeService.getCurrentDate();

        // then
        assertThat(currentDate, equalTo(LocalDate.of(2020, Month.OCTOBER, 10)));
    }
}
