export interface EventDetail {
  id: number;
  name: string;
  description: string;
  eventDate: string;
  location: string;
  categories: { id: number; name: string }[];
}
