import { Component } from '@angular/core';
import { EventService } from '../../../services/event/event.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EventDetail } from '../../../types/event/event-detail';
import { Category } from '../../../types/category/category';
import { CategoryService } from '../../../services/category/category.service';

@Component({
  selector: 'app-update-event',
  imports: [ReactiveFormsModule],
  providers: [EventService],
  templateUrl: './update-event.component.html',
  styleUrl: './update-event.component.css'
})
export class UpdateEventComponent {
  eventForm: FormGroup;
  event?: EventDetail;
  categories: Category[] = [];
  loading = true;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
    private eventService: EventService,
    private categoryService: CategoryService,
    private toastrService: ToastrService,
  ) {
    this.eventForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: [''],
      eventDate: ['', [Validators.required]],
      location: ['', [Validators.required, Validators.minLength(3)]],
      categoryIds: [[], [Validators.required]],
    });
  }

  reloadCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: (data) => {
        this.categories = data;
      },
      error: () => {
        this.toastrService.error('Ocorreu um erro. Tente novamente mais tarde.');
      },
    });
  }

  reloadEvent(): void {
    const eventId = Number(this.route.snapshot.paramMap.get('id'));
    this.eventService.getEventById(eventId).subscribe({
      next: (data) => {
        this.eventForm.patchValue({
          name: data.name,
          description: data.description,
          eventDate: data.eventDate,
          location: data.location,
          categoryIds: data.categories.map((category) => category.id),
        });
        this.event = data as unknown as EventDetail;
        this.loading = false;
      },
      error: () => {
        this.toastrService.error('Ocorreu um erro. Tente novamente mais tarde.');
        this.loading = false;
      },
    });
  }

  ngOnInit(): void {
    this.reloadCategories();
    this.reloadEvent();
  }

  submit(): void {
    const eventId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.eventForm.valid) {
      this.eventService.updateEvent(eventId, this.eventForm.value).subscribe({
        next: () => {
          this.toastrService.success('Evento atualizado com sucesso!');
          this.router.navigate([`events/${eventId}`]);
        },
        error: () => {
          this.toastrService.error('Ocorreu um erro ao atualizar o evento. Tente novamente mais tarde.')
        },
      });
    }
  }

  backToList(): void {
    this.router.navigate(['events']);
  }

}
