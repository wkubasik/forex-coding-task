package com.ubs.forex.validations.web;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.model.ValidationResponse;
import com.ubs.forex.validations.validation.TransactionValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private TransactionValidationService transactionValidationService;

    @Captor
    private ArgumentCaptor<List<Transaction>> transactionsArgumentCaptor;

    @Test
    void shouldGetSuccessResponse() {
        // given
        List<ValidationResponse> validationResponseList = Collections.singletonList(
                new ValidationResponse("message", 1));
        List<Transaction> requestBody = Collections.singletonList(new Transaction());
        given(transactionValidationService.validate(transactionsArgumentCaptor.capture()))
                .willReturn(validationResponseList);
        String url = "http://localhost:" + port + "/transactions/validate";

        // when
        ResponseEntity<ValidationResponse[]> responseEntity = restTemplate.postForEntity(url, requestBody,
                ValidationResponse[].class);

        // then
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(Objects.requireNonNull(responseEntity.getBody()).length, equalTo(1));
        List<Transaction> transactionsArgumentCaptorValue = transactionsArgumentCaptor.getValue();
        assertThat(transactionsArgumentCaptorValue.size(), equalTo(1));
    }
}
