import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../../types/category/category';
import { CategoryDetail } from '../../types/category/category-detail';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private baseUrl = 'http://localhost:8080/categories';

  constructor(private httpClient: HttpClient) {}

  getCategories(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.baseUrl);
  }

  getCategoryById(id: number): Observable<CategoryDetail> {
    return this.httpClient.get<CategoryDetail>(`${this.baseUrl}/${id}`);
  }
}
