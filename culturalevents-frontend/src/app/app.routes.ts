import { Routes } from '@angular/router';
import { ListEventComponent } from './pages/events/list-event/list-event.component';
import { DetailEventComponent } from './pages/events/detail-event/detail-event.component';
import { CreateEventComponent } from './pages/events/create-event/create-event.component';
import { UpdateEventComponent } from './pages/events/update-event/update-event.component';
import { FilterEventComponent } from './pages/events/filter-event/filter-event.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'events',
    pathMatch: 'full',
  },
  {
    path: 'events',
    component: ListEventComponent,
  },
  {
    path: 'events/new',
    component: CreateEventComponent,
  },
  {
    path: 'events/filter',
    component: FilterEventComponent,
  },
  {
    path: 'events/:id',
    component: DetailEventComponent,
  },
  {
    path: 'events/edit/:id',
    component: UpdateEventComponent,
  },
];
