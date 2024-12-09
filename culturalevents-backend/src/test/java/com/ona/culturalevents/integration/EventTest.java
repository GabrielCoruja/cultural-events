package com.ona.culturalevents.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ona.culturalevents.entity.Category;
import com.ona.culturalevents.entity.Event;
import com.ona.culturalevents.repository.CategoryRepository;
import com.ona.culturalevents.repository.EventRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class EventTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private EventRepository eventRepository;

  @MockitoBean
  private CategoryRepository categoryRepository;

  private String asJsonString(Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("GET /events return all events")
  public void getAllEventsSuccessTest() throws Exception {
    List<Event> events = List.of(
        new Event(1L, "Event A", "Description A", LocalDateTime.now().plusDays(1), "Location A",
            new ArrayList<>()),
        new Event(2L, "Event B", "Description B", LocalDateTime.now().plusDays(2), "Location B",
            new ArrayList<>())
    );

    Mockito.when(eventRepository.findAll()).thenReturn(events);

    mockMvc.perform(get("/events"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Event A"))
        .andExpect(jsonPath("$[1].id").value(2L))
        .andExpect(jsonPath("$[1].name").value("Event B"));
  }

  @Nested
  @DisplayName("GET /events/{id} find one event")
  class FindEventByIdTest {

    @Test
    @DisplayName("Success - return one event")
    public void getOneEventSuccessTest() throws Exception {

      Event event = new Event(1L, "Event A", "Description A", LocalDateTime.now().plusDays(1),
          "Location A", new ArrayList<>());

      event.getCategories().add(new Category(1L, "Festas"));
      event.getCategories().add(new Category(2L, "Shows"));

      Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

      DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
      String formattedEventDate = event.getEventDate().format(formatter);

      mockMvc.perform(get("/events/1"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(event.getId()))
          .andExpect(jsonPath("$.name").value(event.getName()))
          .andExpect(jsonPath("$.description").value(event.getDescription()))
          .andExpect(jsonPath("$.eventDate").value(formattedEventDate))
          .andExpect(jsonPath("$.location").value(event.getLocation()))
          .andExpect(jsonPath("$.categories[0].id").value(event.getCategories().get(0).getId()))
          .andExpect(jsonPath("$.categories[0].name").value(event.getCategories().get(0).getName()))
          .andExpect(jsonPath("$.categories[1].id").value(event.getCategories().get(1).getId()))
          .andExpect(
              jsonPath("$.categories[1].name").value(event.getCategories().get(1).getName()));
    }

    @Test
    @DisplayName("Failed - event not found")
    public void getOneEventNotFoundTest() throws Exception {
      Mockito.when(eventRepository.findById(999L)).thenReturn(Optional.empty());

      mockMvc.perform(get("/events/999"))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.error").value("Event Not Found"));
    }
  }

  @Test
  @DisplayName("POST /events create an event")
  public void createEventSuccessTest() throws Exception {
    Event savedEvent = new Event(1L, "New Event", "Description", LocalDateTime.now().plusDays(5),
        "Location", new ArrayList<>());

    String requestJson = """
        {
          "name": "New Event",
          "description": "Description",
          "eventDate": "2024-12-25T19:30:00",
          "location": "Location",
          "categoryIds": [1, 2]
        }
        """;

    List<Category> categories = new ArrayList<>();
    categories.add(new Category(1L, "Festas"));
    categories.add(new Category(2L, "Shows"));

    savedEvent.getCategories().addAll(categories);

    Mockito.when(eventRepository.save(any())).thenReturn(savedEvent);
    Mockito.when(categoryRepository.findAllById(any())).thenReturn(categories);

    mockMvc.perform(post("/events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(savedEvent.getId()))
        .andExpect(jsonPath("$.name").value(savedEvent.getName()))
        .andExpect(jsonPath("$.description").value(savedEvent.getDescription()))
        .andExpect(jsonPath("$.location").value(savedEvent.getLocation()));
  }


  @Nested
  @DisplayName("UPDATE /events/{id} find and update event")
  class UpdateCategoryByIdTest {

    @Test
    @DisplayName("Success - Update an event with success")
    public void updateCategorySuccessTest() throws Exception {
      Event savedEvent = new Event(1L, "Update Event", "Update Description",
          LocalDateTime.now().plusDays(5),
          "Location Update", new ArrayList<>());

      String requestJson = """
          {
            "name": "Update Event",
            "description": "Update Description",
            "eventDate": "2024-12-25T19:30:00",
            "location": "Location Update",
            "categoryIds": [1, 2]
          }
          """;

      List<Category> categories = new ArrayList<>();
      categories.add(new Category(1L, "Festas"));
      categories.add(new Category(2L, "Shows"));

      savedEvent.getCategories().addAll(categories);

      Mockito.when(eventRepository.findById(any())).thenReturn(Optional.of(savedEvent));
      Mockito.when(categoryRepository.findAllById(any())).thenReturn(categories);
      Mockito.when(eventRepository.save(any())).thenReturn(savedEvent);

      mockMvc.perform(post("/events")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestJson))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id").value(savedEvent.getId()))
          .andExpect(jsonPath("$.name").value(savedEvent.getName()))
          .andExpect(jsonPath("$.description").value(savedEvent.getDescription()))
          .andExpect(jsonPath("$.location").value(savedEvent.getLocation()));
    }

    @Test
    @DisplayName("Failed - event not found")
    public void updateCategoryNotFoundTest() throws Exception {
      Mockito.when(eventRepository.findById(any())).thenReturn(Optional.empty());

      String requestJson = """
          {
            "name": "Update Event",
            "description": "Description",
            "eventDate": "2024-12-25T19:30:00",
            "location": "Location",
            "categoryIds": [1, 2]
          }
          """;

      mockMvc.perform(put("/events/999")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestJson))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.error").value("Event Not Found"));
    }
  }

  @Nested
  @DisplayName("DELETE /events/{id} delete an event")
  class DeleteEventTest {

    @Test
    @DisplayName("Success - delete event")
    public void deleteEventSuccessTest() throws Exception {
      Mockito.doNothing().when(eventRepository).deleteById(1L);
      Mockito.when(eventRepository.existsById(any())).thenReturn(true);

      mockMvc.perform(delete("/events/1"))
          .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Failed - event not found")
    public void deleteEventNotFoundTest() throws Exception {
      Mockito.when(categoryRepository.existsById(any())).thenReturn(false);

      mockMvc.perform(delete("/events/999"))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.error").value("Event Not Found"));
    }
  }
}
