package com.ona.culturalevents.controller.dto.event;

import com.ona.culturalevents.entity.Event;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public record EventCreateDto(

    @NotBlank(message = "Name is Required")
    @Size(min = 3, message = "Name must have at least 3 characters")
    String name,

    String description,

    @Future(message = "Event date must be in the future")
    LocalDateTime eventDate,

    @NotBlank(message = "Location is required")
    String location,

    @NotNull(message = "At least one category is required")
    @Size(min = 1, message = "At least one category must be provided")
    List<Long> categoryIds
) {

  public Event toEntity() {
    Event event = new Event();

    event.setName(name);
    event.setDescription(description);
    event.setEventDate(eventDate);
    event.setLocation(location);

    return event;
  }


}
