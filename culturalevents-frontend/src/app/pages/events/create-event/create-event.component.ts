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
  loginForm: FormGroup;
  categories: Category[] = [];
  loadingCategories = true;
  errorLoadingCategories = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private eventService: EventService,
    private categoryService: CategoryService,
    private toastrService: ToastrService,
  ) {
    this.loginForm = this.fb.group({
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
        this.errorLoadingCategories = true;
        this.loadingCategories = false;
      },
    });
  }

  submit(): void {
    if (this.loginForm.valid) {
      this.eventService.createEvent(this.loginForm.value).subscribe({
        next: () => {
          this.toastrService.success('Evento criado com sucesso!');
          this.router.navigate(['events']);
        },
        error: () => {
          this.toastrService.error('Ocorreu um erro ao criar o evento. Tente novamente mais tarde.')
        },
      });
    }
  }
}
