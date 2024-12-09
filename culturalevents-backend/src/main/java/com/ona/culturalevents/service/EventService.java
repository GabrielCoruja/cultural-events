package com.ona.culturalevents.service;

import com.ona.culturalevents.entity.Category;
import com.ona.culturalevents.entity.Event;
import com.ona.culturalevents.exception.notfound.CategoryNotFoundExpection;
import com.ona.culturalevents.exception.notfound.EventNotFoundExpection;
import com.ona.culturalevents.repository.CategoryRepository;
import com.ona.culturalevents.repository.EventRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  private final EventRepository eventRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public EventService(EventRepository eventRepository, CategoryRepository categoryRepository) {
    this.eventRepository = eventRepository;
    this.categoryRepository = categoryRepository;
  }

  public List<Event> findAll() {
    return eventRepository.findAll();
  }

  public Event findById(long eventId) throws EventNotFoundExpection {
    return eventRepository.findById(eventId)
        .orElseThrow(EventNotFoundExpection::new);
  }

  public Event create(Event event, List<Long> categoryIds) throws CategoryNotFoundExpection {
    List<Category> findCategories = categoryRepository.findAllById(categoryIds);

    if (findCategories.size() != categoryIds.size()) {
      throw new CategoryNotFoundExpection();
    }

    event.setCategories(findCategories);

    return eventRepository.save(event);
  }

  public Event update(Event event, long eventId, List<Long> categoryIds)
      throws EventNotFoundExpection, CategoryNotFoundExpection {
    Event findEvent = findById(eventId);

    List<Category> findCategories = categoryRepository.findAllById(categoryIds);

    if (findCategories.size() != categoryIds.size()) {
      throw new CategoryNotFoundExpection();
    }

    event.setCategories(findCategories);

    findEvent.setName(event.getName());
    findEvent.setDescription(event.getDescription());
    findEvent.setEventDate(event.getEventDate());
    findEvent.setLocation(event.getLocation());
    findEvent.setCategories(event.getCategories());

    return eventRepository.save(findEvent);
  }

  public void remove(long categoryId) throws EventNotFoundExpection {
    if (!eventRepository.existsById(categoryId)) {
      throw new EventNotFoundExpection();
    }

    eventRepository.deleteById(categoryId);
  }
}
