package com.ona.culturalevents.controller;

import com.ona.culturalevents.controller.dto.event.EventCreateDto;
import com.ona.culturalevents.controller.dto.event.EventDto;
import com.ona.culturalevents.controller.dto.event.EventWithCategoriesDto;
import com.ona.culturalevents.docs.EventDoc;
import com.ona.culturalevents.entity.Event;
import com.ona.culturalevents.exception.notfound.CategoryNotFoundExpection;
import com.ona.culturalevents.exception.notfound.EventNotFoundExpection;
import com.ona.culturalevents.service.EventService;
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
@RequestMapping(value = "/events")
public class EventController implements EventDoc {

  private final EventService eventService;

  @Autowired
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @Override
  @GetMapping
  public ResponseEntity<List<EventDto>> getAllEvents() {
    List<Event> categories = eventService.findAll();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(categories.stream().map(EventDto::fromEntity).toList());
  }

  @Override
  @GetMapping("/{eventId}")
  public ResponseEntity<EventWithCategoriesDto> getEventById(@PathVariable Long eventId)
      throws EventNotFoundExpection {
    Event Event = eventService.findById(eventId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(EventWithCategoriesDto.fromEntity(Event));
  }

  @Override
  @PostMapping
  public ResponseEntity<EventWithCategoriesDto> createEvent(
      @Valid @RequestBody EventCreateDto eventCreateDto
  ) throws CategoryNotFoundExpection {
    Event Event = eventService.create(
        eventCreateDto.toEntity(),
        eventCreateDto.categoryIds()
    );

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(EventWithCategoriesDto.fromEntity(Event));
  }

  @Override
  @PutMapping("/{eventId}")
  public ResponseEntity<EventWithCategoriesDto> updateEvent(
      @Valid @RequestBody EventCreateDto eventCreateDto,
      @PathVariable Long eventId
  ) throws EventNotFoundExpection, CategoryNotFoundExpection {
    Event Event = eventService.update(
        eventCreateDto.toEntity(),
        eventId,
        eventCreateDto.categoryIds()
    );

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(EventWithCategoriesDto.fromEntity(Event));
  }

  @Override
  @DeleteMapping("/{eventId}")
  public ResponseEntity<Void> deleteEvent(
      @PathVariable Long eventId
  ) throws EventNotFoundExpection {
    eventService.remove(eventId);

    return ResponseEntity.noContent().build();
  }
}
