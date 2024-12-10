package com.ona.culturalevents.docs;

import com.ona.culturalevents.controller.dto.ErrorDto;
import com.ona.culturalevents.controller.dto.ErrorsValidatorDto;
import com.ona.culturalevents.controller.dto.category.CategoryCreateDto;
import com.ona.culturalevents.controller.dto.category.CategoryDto;
import com.ona.culturalevents.controller.dto.category.CategoryWithEventsDto;
import com.ona.culturalevents.exception.badrequest.DuplicateEntryException;
import com.ona.culturalevents.exception.notfound.CategoryNotFoundExpection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Categorias", description = "Gerenciamento de Categorias de Eventos")
public interface CategoryDoc {

  @Operation(summary = "Listar todas as categorias", description = "Endpoint para listar todas as categorias disponíveis.")
  @ApiResponse(
      responseCode = "200",
      description = "Lista de categorias retornada com sucesso",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = CategoryDto.class),
          examples = @ExampleObject(value = """
                  [
                      {
                          "id": 1,
                          "name": "Festas"
                      },
                      {
                          "id": 2,
                          "name": "Shows"
                      }
                  ]
              """))
  )
  ResponseEntity<List<CategoryDto>> getAllCategories();

  @Operation(summary = "Buscar categoria por ID", description = "Endpoint para buscar uma categoria específica pelo seu ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CategoryWithEventsDto.class),
              examples = @ExampleObject(value = """
                      {
                          "id": 1,
                          "name": "Festas",
                          "events": [
                              {
                                  "id": 101,
                                  "name": "Baile de Carnaval",
                                  "description": "Uma festa com máscaras e fantasias.",
                                  "eventDate": "2024-02-10T20:00:00",
                                  "location": "Praça Central"
                              }
                          ]
                      }
                  """))),
      @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorDto.class)
          ))
  })
  ResponseEntity<CategoryWithEventsDto> getCategoryById(@PathVariable Long categoryId)
      throws CategoryNotFoundExpection;

  @Operation(summary = "Criar uma nova categoria", description = "Endpoint para criar uma nova categoria.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CategoryDto.class),
              examples = @ExampleObject(value = """
                      {
                          "id": 1,
                          "name": "Festas Temáticas"
                      }
                  """
              ))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorsValidatorDto.class)
          ))
  })
  ResponseEntity<CategoryDto> createCategory(CategoryCreateDto categoryCreateDto)
      throws DuplicateEntryException;

  @Operation(summary = "Atualizar uma categoria", description = "Endpoint para atualizar as informações de uma categoria.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CategoryDto.class),
              examples = @ExampleObject(value = """
                      {
                          "id": 1,
                          "name": "Festas Temáticas"
                      }
                  """))),
      @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorDto.class)
          )),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorsValidatorDto.class)
          ))
  })
  ResponseEntity<CategoryDto> updateCategory(CategoryCreateDto categoryCreateDto,
      @PathVariable Long categoryId) throws CategoryNotFoundExpection, DuplicateEntryException;

  @Operation(summary = "Excluir uma categoria", description = "Endpoint para excluir uma categoria pelo seu ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso"),
      @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorDto.class)
          ))
  })
  ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId)
      throws CategoryNotFoundExpection;
}
