package com.ona.culturalevents.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ona.culturalevents.entity.Category;
import com.ona.culturalevents.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CategoryRepository categoryRepository;

  private String asJsonString(Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("GET /categories return all categories")
  public void getAllCategoriesSuccessTest() throws Exception {
    List<Category> categories = new ArrayList<>();
    categories.add(new Category(1L, "Festas"));
    categories.add(new Category(2L, "Shows"));

    Mockito.when(categoryRepository.findAll()).thenReturn(categories);

    String url = "/categories";

    mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Festas"))
        .andExpect(jsonPath("$[1].id").value(2L))
        .andExpect(jsonPath("$[1].name").value("Shows"));
  }

  @Nested
  @DisplayName("GET /categories/{id} find one category")
  class FindCategoryByIdTest {

    @Test
    @DisplayName("Success - return one category")
    public void getOneCategorieSuccessTest() throws Exception {
      Category categorie = new Category(1L, "Festas");
      categorie.setEvents(new ArrayList<>());

      Mockito.when(categoryRepository.findById(any()))
          .thenReturn(Optional.of(categorie));

      String url = "/categories/1";

      mockMvc.perform(get(url))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(1L))
          .andExpect(jsonPath("$.name").value("Festas"))
          .andExpect(jsonPath("$.events").isEmpty());

      Mockito.verify(categoryRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Failed - category not found")
    public void getOneCategorieFailedTest() throws Exception {
      Mockito.when(categoryRepository.findById(any()))
          .thenReturn(Optional.empty());

      String url = "/categories/999";

      mockMvc.perform(get(url))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.error").value("Category Not Found"));
    }
  }

  @Test
  @DisplayName("POST /categories create a categorie")
  public void createCategorieSuccessTest() throws Exception {
    Category newCategory = new Category(null, "Música");
    Category savedCategory = new Category(1L, "Música");

    Mockito.when(categoryRepository.save(any())).thenReturn(savedCategory);
    Mockito.when(categoryRepository.findByName(any())).thenReturn(Optional.empty());

    String url = "/categories";

    mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(newCategory)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Música"));

    Mockito.verify(categoryRepository).findByName(eq(newCategory.getName()));
  }

  @Nested
  @DisplayName("DELETE /categories/{id} find and delete category")
  class DeleteCategoryByIdTest {

    @Test
    @DisplayName("Success - Delete a category with success")
    public void deleteCategorySuccessTest() throws Exception {
      Long categoryId = 1L;
      Mockito.doNothing().when(categoryRepository).deleteById(categoryId);
      Mockito.when(categoryRepository.existsById(categoryId)).thenReturn(true);

      String url = "/categories/1";

      // Act & Assert
      mockMvc.perform(delete(url))
          .andExpect(status().isNoContent());

      Mockito.verify(categoryRepository).deleteById(eq(categoryId));
    }

    @Test
    @DisplayName("Failed - Category not found")
    public void deleteCategoryNotFoundTest() throws Exception {
      Long categoryId = 999L;
      Mockito.when(categoryRepository.existsById(categoryId)).thenReturn(false);

      String url = "/categories/999";

      mockMvc.perform(delete(url))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.error").value("Category Not Found"));
    }
  }

  @Nested
  @DisplayName("UPDATE /categories/{id} find and update category")
  class UpdateCategoryByIdTest {

    @Test
    @DisplayName("Success - Update a category with success")
    public void updateCategorySuccessTest() throws Exception {
      Category updateCategory = new Category(null, "Música");
      Category findCategory = new Category(1L, "Festas");
      Category updateWithNewDataCategory = new Category(1L, "Música");

      Long categoryId = updateWithNewDataCategory.getId();
      Mockito
          .when(categoryRepository.findById(categoryId))
          .thenReturn(Optional.of(findCategory));

      Mockito.when(categoryRepository.findByName(any())).thenReturn(Optional.empty());

      Mockito.when(categoryRepository.save(any())).thenReturn(updateWithNewDataCategory);

      String url = "/categories/1";

      mockMvc.perform(put(url)
              .contentType(MediaType.APPLICATION_JSON)
              .content(asJsonString(updateCategory)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(categoryId))
          .andExpect(jsonPath("$.name").value("Música"));
    }

    @Test
    @DisplayName("Failed - category not found")
    public void updateCategoryNotFoundTest() throws Exception {
      Category updateCategory = new Category(null, "Música");

      Long categoryId = 999L;
      Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

      String url = "/categories/999";

      mockMvc.perform(put(url)
              .contentType(MediaType.APPLICATION_JSON)
              .content(asJsonString(updateCategory)))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.error").value("Category Not Found"));
    }
  }
}
