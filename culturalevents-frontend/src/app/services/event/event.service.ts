import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EventDetail } from '../../types/event-detail';

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
}
