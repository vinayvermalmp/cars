package com.preowned.cars.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// A class to get more detail back to the client (by using the HTTP response body)
public class ErrorResponse {
    private LocalDateTime timeStamp;
    private String message;
    private String path;
    private HttpStatus errorCode;
}
