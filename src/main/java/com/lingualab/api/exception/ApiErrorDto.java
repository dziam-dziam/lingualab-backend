package com.lingualab.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDto {
    private Instant timestamp;
    private int status;
    private String message;
    private Map<String, String> errors;
}
