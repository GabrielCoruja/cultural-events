import { TestBed } from '@angular/core/testing';
import { HttpTestingController } from '@angular/common/http/testing';

import { testProviders } from '../../../testing/test-providers';
import { CategoryService } from './category.service';

describe('CategoryService', () => {
  let service: CategoryService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({ providers: testProviders });
    service = TestBed.inject(CategoryService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it('busca a lista de categorias', () => {
    service.getCategories().subscribe((categories) => expect(categories.length).toBe(2));

    const req = http.expectOne('http://localhost:8080/categories');
    expect(req.request.method).toBe('GET');
    req.flush([{ id: 1, name: 'Música' }, { id: 2, name: 'Teatro' }]);
  });
});
