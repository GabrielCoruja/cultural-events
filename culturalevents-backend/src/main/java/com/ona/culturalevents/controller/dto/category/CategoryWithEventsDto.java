package com.ona.culturalevents.controller.dto.category;

import com.ona.culturalevents.controller.dto.event.EventDto;
import com.ona.culturalevents.entity.Category;
import java.util.List;

public record CategoryWithEventsDto(
    Long id,
    String name,
    List<EventDto> events
) {

  public static CategoryWithEventsDto fromEntity(Category category) {
    return new CategoryWithEventsDto(
        category.getId(),
        category.getName(),
        category.getEvents().stream().map(EventDto::fromEntity).toList()
    );
  }

}
