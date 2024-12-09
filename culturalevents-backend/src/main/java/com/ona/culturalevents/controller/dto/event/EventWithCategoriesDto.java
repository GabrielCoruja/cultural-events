package com.ona.culturalevents.controller.dto.event;

import com.ona.culturalevents.controller.dto.category.CategoryDto;
import com.ona.culturalevents.entity.Event;
import java.time.LocalDateTime;
import java.util.List;

public record EventWithCategoriesDto(
    Long id,
    String name,
    String description,
    LocalDateTime eventDate,
    String location,
    List<CategoryDto> categories
) {

  public static EventWithCategoriesDto fromEntity(Event event) {
    return new EventWithCategoriesDto(
        event.getId(),
        event.getName(),
        event.getDescription(),
        event.getEventDate(),
        event.getLocation(),
        event.getCategories()
            .stream()
            .map(CategoryDto::fromEntity)
            .toList()
    );
  }

}
