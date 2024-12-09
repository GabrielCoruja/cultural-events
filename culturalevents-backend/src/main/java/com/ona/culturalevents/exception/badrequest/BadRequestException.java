package com.ona.culturalevents.exception.badrequest;

public abstract class BadRequestException extends Exception {

  public BadRequestException(String message) {
    super(message);
  }

}
