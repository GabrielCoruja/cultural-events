package com.ona.culturalevents.controller;

import com.ona.culturalevents.controller.dto.category.CategoryCreateDto;
import com.ona.culturalevents.controller.dto.category.CategoryDto;
import com.ona.culturalevents.controller.dto.category.CategoryWithEventsDto;
import com.ona.culturalevents.entity.Category;
import com.ona.culturalevents.exception.badrequest.DuplicateEntryException;
import com.ona.culturalevents.exception.notfound.CategoryNotFoundExpection;
import com.ona.culturalevents.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    List<Category> categories = categoryService.findAll();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(categories.stream().map(CategoryDto::fromEntity).toList());
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryWithEventsDto> getCategoryById(@PathVariable Long categoryId)
      throws CategoryNotFoundExpection {
    Category category = categoryService.findById(categoryId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(CategoryWithEventsDto.fromEntity(category));
  }

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(
      @Valid @RequestBody CategoryCreateDto categoryCreateDto
  ) throws DuplicateEntryException {
    Category category = categoryService.create(categoryCreateDto.toEntity());

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(CategoryDto.fromEntity(category));
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> updateCategory(
      @Valid @RequestBody CategoryCreateDto categoryCreateDto,
      @PathVariable Long categoryId
  ) throws CategoryNotFoundExpection, DuplicateEntryException {
    Category category = categoryService.update(categoryCreateDto.toEntity(), categoryId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(CategoryDto.fromEntity(category));
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> deleteCategory(
      @PathVariable Long categoryId
  ) throws CategoryNotFoundExpection {
    categoryService.remove(categoryId);

    return ResponseEntity.noContent().build();
  }
}
