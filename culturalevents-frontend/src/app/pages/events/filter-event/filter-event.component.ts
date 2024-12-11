import { Component } from '@angular/core';
import { CategoryService } from '../../../services/category/category.service';
import { Category } from '../../../types/category/category';
import { ReactiveFormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Event as IEvent } from '../../../types/event/event';

@Component({
  selector: 'app-filter-event',
  imports: [ReactiveFormsModule],
  providers: [CategoryService],
  templateUrl: './filter-event.component.html',
  styleUrl: './filter-event.component.css'
})
export class FilterEventComponent {
  categories: Category[] = [];
  events: IEvent[] = [];
  loadingCategories = true;
  errorLoadingCategories = false;

  constructor(
    private router: Router,
    private categoryService: CategoryService,
    private toastrService: ToastrService,
  ) { }

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

  filterEvents(event: Event): void {
    const categoryId = Number((event.target as HTMLTextAreaElement).value);
    this.categoryService.getCategoryById(categoryId).subscribe({
      next: (data) => {
        this.events = data.events as unknown as IEvent[];
        console.log(data);
      },
      error: () => {
        this.toastrService.error('Ocorreu um erro. Tente novamente mais tarde.');
      },
    });
  }

  detailEvent(id: number): void {
    this.router.navigate([`events/${id}`]);
  }

  createNewEventPage(): void {
    this.router.navigate(['events/new']);
  }

  backToList(): void {
    this.router.navigate(['events']);
  }
}
