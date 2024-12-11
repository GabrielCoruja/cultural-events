import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from '../../../services/event/event.service';
import { EventDetail } from '../../../types/event/event-detail';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-detail',
  imports: [],
  providers: [EventService],
  templateUrl: './detail-event.component.html',
  styleUrl: './detail-event.component.css'
})
export class DetailEventComponent {
  event?: EventDetail;
  loading = true;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventService: EventService,
    private toastrService: ToastrService,
  ) {}

  ngOnInit(): void {
    const eventId = Number(this.route.snapshot.paramMap.get('id'));
    this.eventService.getEventById(eventId).subscribe({
      next: (data) => {
        this.event = data as unknown as EventDetail;
        this.loading = false;
      },
      error: () => {
        this.toastrService.error('Ocorreu um erro. Tente novamente mais tarde.');
        this.loading = false;
      },
    });
  }

  backToList(): void {
    this.router.navigate(['events']);
  }

  updateEventPage(): void {
    const eventId = Number(this.route.snapshot.paramMap.get('id'));
    this.router.navigate([`events/edit/${eventId}`]);
  }

  deleteEvent(): void {
    const eventId = Number(this.route.snapshot.paramMap.get('id'));
    this.eventService.deleteEvent(eventId).subscribe({
      next: () => {
        this.toastrService.success('Evento deletado com sucesso!');
        this.router.navigate(['events']);
      },
      error: () => {
        this.toastrService.error('Ocorreu um erro ao deletar o evento. Tente novamente mais tarde.')
      },
    });
  }
}
