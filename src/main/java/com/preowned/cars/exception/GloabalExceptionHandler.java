package com.preowned.cars.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GloabalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CarRegNoAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleRegNoAlreadyExist(CarRegNoAlreadyExistsException exception, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                //"CAR ALREADY EXISTS"
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarRegNoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRegNoNotFoundException(CarRegNoNotFoundException ex,
                                                                      WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                //CAR REGISTRATION NOT FOUND
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CarRegMismatchException.class)
    public ResponseEntity<ErrorResponse> handleRegMisMatchException(CarRegMismatchException ex,
                                                                      WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                //CAR REGISTRATION NUMBER MISMATCH
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        objectErrors.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
