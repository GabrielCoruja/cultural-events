package com.ona.culturalevents.exception.notfound;

public class EventNotFoundExpection extends NotFoundException {

  public EventNotFoundExpection() {
    super("Event Not Found");
  }

}
