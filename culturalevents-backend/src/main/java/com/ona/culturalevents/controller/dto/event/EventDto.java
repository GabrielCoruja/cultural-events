package com.ona.culturalevents.controller.dto.event;

import com.ona.culturalevents.entity.Event;
import java.time.LocalDateTime;

public record EventDto(
    Long id,
    String name,
    String description,
    LocalDateTime eventDate,
    String location
) {

  public static EventDto fromEntity(Event event) {
    return new EventDto(
        event.getId(),
        event.getName(),
        event.getDescription(),
        event.getEventDate(),
        event.getLocation()
    );
  }

}
