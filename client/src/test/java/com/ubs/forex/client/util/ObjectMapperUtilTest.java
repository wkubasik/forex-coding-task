package com.ubs.forex.client.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
public class ObjectMapperUtilTest {

    @InjectMocks
    private ObjectMapperUtil util;
    private final ObjectMapper objectMapper = mock(ObjectMapper.class);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(util, "objectMapper", objectMapper);
    }

    @Test
    void shouldMapToJson() throws JsonProcessingException {
        // given
        Object object = new Object();
        given(objectMapper.writeValueAsString(object)).willReturn("json");

        // when
        String result = util.writeValue(object);

        // then
        assertThat(result, equalTo("json"));
    }

    @Test
    void shouldGetNullWhenExceptionWhileMappingToJson(CapturedOutput output) throws JsonProcessingException {
        // given
        Object object = new Object();
        given(objectMapper.writeValueAsString(object)).willThrow(mock(JsonProcessingException.class));

        // when
        String result = util.writeValue(object);

        // then
        assertThat(result, nullValue());
        assertThat(output.getAll(), containsString("Error while writing object value"));
    }

    @Test
    void shouldMapToObject() throws JsonProcessingException {
        // given
        String json = "json";
        Object object = new Object();
        given(objectMapper.readValue(json, Object.class)).willReturn(object);

        // when
        Object result = util.readValue(json, Object.class);

        // then
        assertThat(result, equalTo(object));
    }

    @Test
    void shouldGetNullWhenExceptionWhileMappingToObject(CapturedOutput output) throws JsonProcessingException {
        // given
        String json = "json";
        given(objectMapper.readValue(json, Object.class)).willThrow(mock(JsonProcessingException.class));

        // when
        Object result = util.readValue(json, Object.class);

        // then
        assertThat(result, nullValue());
        assertThat(output.getAll(), containsString("Error while reading json value"));
    }
}
