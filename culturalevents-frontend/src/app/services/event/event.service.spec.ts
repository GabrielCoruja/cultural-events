import { TestBed } from '@angular/core/testing';
import { HttpTestingController } from '@angular/common/http/testing';

import { testProviders } from '../../../testing/test-providers';
import { EventService } from './event.service';

describe('EventService', () => {
  let service: EventService;
  let http: HttpTestingController;
  const baseUrl = 'http://localhost:8080/events';

  beforeEach(() => {
    TestBed.configureTestingModule({ providers: testProviders });
    service = TestBed.inject(EventService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it('busca a lista de eventos', () => {
    service.getEvents().subscribe((events) => expect(events.length).toBe(1));

    const req = http.expectOne(baseUrl);
    expect(req.request.method).toBe('GET');
    req.flush([{ id: 1, name: 'Show de Jazz' }]);
  });

  it('busca um evento pelo id', () => {
    service.getEventById(3).subscribe();

    expect(http.expectOne(`${baseUrl}/3`).request.method).toBe('GET');
  });

  it('remove um evento pelo id', () => {
    service.deleteEvent(3).subscribe();

    expect(http.expectOne(`${baseUrl}/3`).request.method).toBe('DELETE');
  });
});
