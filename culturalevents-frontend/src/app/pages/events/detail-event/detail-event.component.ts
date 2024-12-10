import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from '../../../services/event/event.service';
import { EventDetail } from '../../../types/event-detail';

@Component({
  selector: 'app-detail',
  imports: [],
  providers: [EventService],
  templateUrl: './detail-event.component.html',
  styleUrl: './detail-event.component.css'
})
export class DetailEventComponent {
[x: string]: any;
  event?: EventDetail;
  loading = true;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventService: EventService
  ) {}

  ngOnInit(): void {
    const eventId = Number(this.route.snapshot.paramMap.get('id'));
    this.eventService.getEventById(eventId).subscribe({
      next: (data) => {
        this.event = data as unknown as EventDetail;
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      },
    });
  }

  backToList(): void {
    this.router.navigate(['events']);
  }

}
