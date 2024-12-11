import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EventDetail } from '../../types/event/event-detail';
import { EventCreate } from '../../types/event/event-create';
import { Event } from '../../types/event/event';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private baseUrl = 'http://localhost:8080/events';

  constructor(private httpClient: HttpClient) {}

  getEvents(): Observable<Event[]> {
    return this.httpClient.get<Event[]>(this.baseUrl);
  }

  getEventById(id: number): Observable<EventDetail> {
    return this.httpClient.get<EventDetail>(`${this.baseUrl}/${id}`);
  }

  createEvent(event: Omit<EventCreate, 'id'>): Observable<EventDetail> {
    return this.httpClient.post<EventDetail>(this.baseUrl, event);
  }

  deleteEvent(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
  }

  updateEvent(id: number, event: Partial<Event>): Observable<Event> {
    return this.httpClient.put<Event>(`${this.baseUrl}/${id}`, event);
  }
}
