package com.ona.culturalevents.docs;

import com.ona.culturalevents.controller.dto.ErrorDto;
import com.ona.culturalevents.controller.dto.ErrorsValidatorDto;
import com.ona.culturalevents.controller.dto.event.EventCreateDto;
import com.ona.culturalevents.controller.dto.event.EventDto;
import com.ona.culturalevents.controller.dto.event.EventWithCategoriesDto;
import com.ona.culturalevents.exception.notfound.CategoryNotFoundExpection;
import com.ona.culturalevents.exception.notfound.EventNotFoundExpection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Eventos", description = "Gerenciamento de Eventos Culturais")
public interface EventDoc {

  @Operation(summary = "Listar todos os eventos", description = "Endpoint para listar todos os eventos disponíveis.")
  @ApiResponse(
      responseCode = "200",
      description = "Lista de eventos retornada com sucesso",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = EventDto.class))
  )
  ResponseEntity<List<EventDto>> getAllEvents();

  @Operation(summary = "Buscar evento por ID", description = "Endpoint para buscar um evento específico pelo seu ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = EventWithCategoriesDto.class))),
      @ApiResponse(responseCode = "404", description = "Evento não encontrado",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorDto.class)))
  })
  ResponseEntity<EventWithCategoriesDto> getEventById(@PathVariable Long eventId)
      throws EventNotFoundExpection;

  @Operation(summary = "Criar um novo evento", description = "Endpoint para criar um novo evento cultural.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Evento criado com sucesso",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = EventWithCategoriesDto.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
          content = @Content(mediaType = "application/json"))
  })
  ResponseEntity<EventWithCategoriesDto> createEvent(
      @RequestBody(
          description = "Dados do evento a ser criado",
          required = true,
          content = @Content(schema = @Schema(implementation = EventCreateDto.class),
              examples = @ExampleObject(value = """
                      {
                          "name": "Baile de Carnaval",
                          "description": "Uma festa com máscaras e fantasias.",
                          "eventDate": "2080-02-10T20:00:00",
                          "location": "Praça Central",
                          "categoryIds": [1, 2]
                      }
                  """))
      )
      EventCreateDto eventCreateDto
  ) throws CategoryNotFoundExpection;

  @Operation(summary = "Atualizar um evento", description = "Endpoint para atualizar as informações de um evento.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = EventWithCategoriesDto.class))),
      @ApiResponse(responseCode = "404", description = "Evento não encontrado",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorDto.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorsValidatorDto.class)))
  })
  ResponseEntity<EventWithCategoriesDto> updateEvent(
      @RequestBody(
          description = "Dados do evento a ser atualizado",
          required = true,
          content = @Content(schema = @Schema(implementation = EventCreateDto.class),
              examples = @ExampleObject(value = """
                      {
                          "name": "Baile de Carnaval",
                          "description": "Uma festa com máscaras e fantasias.",
                          "eventDate": "2080-02-10T20:00:00",
                          "location": "Praça Central",
                          "categoryIds": [1, 2]
                      }
                  """))
      )
      EventCreateDto eventCreateDto,
      @PathVariable Long eventId
  ) throws EventNotFoundExpection, CategoryNotFoundExpection;

  @Operation(summary = "Excluir um evento", description = "Endpoint para excluir um evento pelo seu ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Evento excluído com sucesso"),
      @ApiResponse(responseCode = "404", description = "Evento não encontrado",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
  })
  ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) throws EventNotFoundExpection;
}
