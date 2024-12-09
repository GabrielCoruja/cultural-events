package com.ona.culturalevents.service;

import com.ona.culturalevents.entity.Category;
import com.ona.culturalevents.exception.badrequest.DuplicateEntryException;
import com.ona.culturalevents.exception.notfound.CategoryNotFoundExpection;
import com.ona.culturalevents.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public Category findById(long categoryId) throws CategoryNotFoundExpection {
    return categoryRepository.findById(categoryId)
        .orElseThrow(CategoryNotFoundExpection::new);
  }

  private void validateUniqueName(String name) throws DuplicateEntryException {
    if (categoryRepository.findByName(name).isPresent()) {
      throw new DuplicateEntryException("Category name must be unique.");
    }
  }

  public Category create(Category category) throws DuplicateEntryException {
    validateUniqueName(category.getName());

    return categoryRepository.save(category);
  }

  public Category update(Category category, long categoryId)
      throws CategoryNotFoundExpection, DuplicateEntryException {
    Category findCategory = findById(categoryId);

    validateUniqueName(category.getName());

    findCategory.setName(category.getName());

    return categoryRepository.save(findCategory);
  }

  public void remove(long categoryId) throws CategoryNotFoundExpection {
    if (!categoryRepository.existsById(categoryId)) {
      throw new CategoryNotFoundExpection();
    }

    categoryRepository.deleteById(categoryId);
  }
}
