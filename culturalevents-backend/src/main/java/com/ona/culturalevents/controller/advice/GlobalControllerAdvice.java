package com.ona.culturalevents.controller.advice;

import com.ona.culturalevents.controller.dto.ErrorDto;
import com.ona.culturalevents.controller.dto.ErrorsValidatorDto;
import com.ona.culturalevents.exception.badrequest.BadRequestException;
import com.ona.culturalevents.exception.notfound.NotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDto> handleNotFound(NotFoundException exception) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorDto(exception.getMessage()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorDto> handleBadRequest(BadRequestException exception) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorDto(exception.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorsValidatorDto> handleMethodArgumentNotValidError(
      MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : exception.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ErrorsValidatorDto(errors));
  }
}
