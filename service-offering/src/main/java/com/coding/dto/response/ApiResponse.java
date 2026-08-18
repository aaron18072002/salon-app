package com.coding.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiResponse<T> {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private int status;
    private String message;
    private T data;

}
