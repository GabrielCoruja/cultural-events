package com.ona.culturalevents.controller.dto.category;

import com.ona.culturalevents.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateDto(

    @NotBlank(message = "Name is Required")
    @Size(min = 3, message = "Name must have at least 3 characters")
    String name
) {

  public Category toEntity() {
    Category category = new Category();

    category.setName(name);

    return category;
  }

}
