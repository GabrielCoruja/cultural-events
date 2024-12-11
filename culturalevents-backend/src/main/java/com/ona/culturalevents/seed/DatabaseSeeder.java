package com.ona.culturalevents.seed;

import com.ona.culturalevents.entity.Category;
import com.ona.culturalevents.entity.Event;
import com.ona.culturalevents.repository.CategoryRepository;
import com.ona.culturalevents.repository.EventRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")

@Component
public class DatabaseSeeder implements CommandLineRunner {

  private final CategoryRepository categoryRepository;
  private final EventRepository eventRepository;

  private List<Category> categories = new ArrayList<>();

  @Autowired
  public DatabaseSeeder(CategoryRepository categoryRepository, EventRepository eventRepository) {
    this.categoryRepository = categoryRepository;
    this.eventRepository = eventRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    seedCategories();
    seedEvents();
  }

  private void seedCategories() {
    categories.add(new Category("Música"));
    categories.add(new Category("Teatro"));
    categories.add(new Category("Dança"));
    categories.add(new Category("Cinema"));
    categories.add(new Category("Literatura"));
    categories.add(new Category("Artes Visuais"));

    categories = categoryRepository.saveAll(categories);
  }

  private void seedEvents() {
    List<Event> events = new ArrayList<>();

    events.add(new Event(
        null,
        "Show de Jazz",
        "Show de Jazz com músicos locais",
        LocalDateTime.now().plusDays(20),
        "Teatro Municipal",
        List.of(categories.get(0), categories.get(2))
    ));

    events.add(new Event(
        null,
        "Espetáculo de Ballet",
        "Espetáculo de Ballet com a Cia. de Dança de São Paulo",
        LocalDateTime.now().plusDays(30),
        "Teatro Municipal",
        List.of(categories.get(1), categories.get(2))
    ));

    events.add(new Event(
        null,
        "Exibição de Filme",
        "Exibição do filme 'Cidade de Deus'",
        LocalDateTime.now().plusDays(40),
        "Cineclube",
        List.of(categories.get(3))
    ));

    events.add(new Event(
        null,
        "Sarau de Poesia",
        "Sarau de Poesia com poetas locais",
        LocalDateTime.now().plusDays(50),
        "Biblioteca Municipal",
        List.of(categories.get(4))
    ));

    events.add(new Event(
        null,
        "Exposição de Pintura",
        "Exposição de Pintura com artistas locais",
        LocalDateTime.now().plusDays(60),
        "Galeria de Arte",
        List.of(categories.get(5))
    ));

    events.add(new Event(
        null,
        "Show de Rock",
        "Show de Rock com bandas locais",
        LocalDateTime.now().plusDays(70),
        "Praça Central",
        List.of(categories.get(0))
    ));

    eventRepository.saveAll(events);
  }
}
