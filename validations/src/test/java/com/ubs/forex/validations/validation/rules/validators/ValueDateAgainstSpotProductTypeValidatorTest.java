package com.ubs.forex.validations.validation.rules.validators;

import com.ubs.forex.validations.common.DateTimeService;
import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.validation.rules.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ValueDateAgainstSpotProductTypeValidatorTest {

    @InjectMocks
    private ValueDateAgainstSpotProductTypeValidator validator;

    @Mock
    private DateTimeService dateTimeService;

    @Test
    void shouldGetSuccessWhenValueDateIsBefore2DaysFromNow() {
        // given
        given(dateTimeService.getCurrentDate()).willReturn(LocalDate.of(2022, Month.JANUARY, 14));
        Transaction transaction = new Transaction();
        transaction.setValueDate(LocalDate.of(2022, Month.JANUARY, 16));

        // when
        ValidationResult result = validator.validate(transaction);

        // then
        assertThat(result.isSuccess(), equalTo(true));
        assertThat(result.getMessage(), nullValue());
    }

    @Test
    void shouldGetFailWhenValueDateIsAfter2DaysFromNow() {
        // given
        given(dateTimeService.getCurrentDate()).willReturn(LocalDate.of(2022, Month.JANUARY, 14));
        Transaction transaction = new Transaction();
        transaction.setValueDate(LocalDate.of(2022, Month.JANUARY, 17));

        // when
        ValidationResult result = validator.validate(transaction);

        // then
        assertThat(result.isSuccess(), equalTo(false));
        assertThat(result.getMessage(),
                equalTo("Value date must be before two days from now. Current date: 2022-01-14"));
    }
}
