import { Routes } from '@angular/router';
import { ListEventComponent } from './pages/events/list-event/list-event.component';
import { DetailEventComponent } from './pages/events/detail-event/detail-event.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'events',
    pathMatch: 'full'
  },
  {
    path: 'events',
    component: ListEventComponent
  },
  {
    path: 'events/:id',
    component: DetailEventComponent
  }
];
