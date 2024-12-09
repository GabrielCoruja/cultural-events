package com.ona.culturalevents.controller.dto.category;

import com.ona.culturalevents.entity.Category;

public record CategoryDto(Long id, String name) {

  public static CategoryDto fromEntity(Category category) {
    return new CategoryDto(
        category.getId(),
        category.getName()
    );
  }

}
