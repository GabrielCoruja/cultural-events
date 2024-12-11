import { Component } from '@angular/core';
import { EventService } from '../../../services/event/event.service';
import { Event } from '../../../types/event/event';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

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
    private toastrService: ToastrService,
  ) {}

  ngOnInit(): void {
    this.eventService.getEvents().subscribe({
      next: (data) => {
        this.events = data as unknown as Event[];
        this.loading = false;
      },
      error: () => {
        this.toastrService.error('Ocorreu um erro. Tente novamente mais tarde.');
        this.loading = false;
      },
    });
  }

  detailEvent(id: number): void {
    this.router.navigate([`events/${id}`]);
  }

  createNewEventPage(): void {
    this.router.navigate(['events/new']);
  }

  filterEvents(): void {
    this.router.navigate(['events/filter']);
  }

}
