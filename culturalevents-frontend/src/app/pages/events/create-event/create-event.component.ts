import { Component } from '@angular/core';
import { EventService } from '../../../services/event/event.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CategoryService } from '../../../services/category/category.service';
import { Category } from '../../../types/category/category';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-event',
  imports: [ReactiveFormsModule],
  providers: [EventService, CategoryService],
  templateUrl: './create-event.component.html',
  styleUrl: './create-event.component.css'
})
export class CreateEventComponent {
  eventForm: FormGroup;
  categories: Category[] = [];
  loadingCategories = true;

  constructor(
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

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe({
      next: (data) => {
        this.categories = data;
        this.loadingCategories = false;
      },
      error: () => {
        this.toastrService.error('Ocorreu um erro. Tente novamente mais tarde.');
        this.loadingCategories = false;
      },
    });
  }

  submit(): void {
    if (this.eventForm.valid) {
      this.eventService.createEvent(this.eventForm.value).subscribe({
        next: () => {
          this.toastrService.success('Evento criado com sucesso!');
          this.router.navigate(['events']);
        },
        error: () => {
          this.toastrService.error('Ocorreu um erro ao criar o evento. Tente novamente mais tarde.');
        },
      });
    }
  }

  backToList(): void {
    this.router.navigate(['events']);
  }
}
