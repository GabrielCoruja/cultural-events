import { Event } from '../../types/event/event';

export interface CategoryDetail {
  id: number;
  name: string;
  events: Event[];
}
