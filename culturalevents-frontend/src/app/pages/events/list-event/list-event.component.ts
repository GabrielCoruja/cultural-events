import { Component } from '@angular/core';
import { EventService } from '../../../services/event/event.service';
import { Event } from '../../../types/event';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list',
  imports: [],
  providers: [EventService],
  templateUrl: './list-event.component.html',
  styleUrl: './list-event.component.css'
})
export class ListEventComponent {
  events: Event[] = [];
  loading = true;
  error = false;

  constructor(
    private eventService: EventService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.eventService.getEvents().subscribe({
      next: (data) => {
        this.events = data as unknown as Event[];
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      },
    });
  }

  detailEvent(id: number): void {
    this.router.navigate([`events/${id}`]);
  }

}
