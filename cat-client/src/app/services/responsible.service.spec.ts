import { TestBed } from '@angular/core/testing';

import { ResponsibleService } from './responsible.service';

describe('ResponsibleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ResponsibleService = TestBed.get(ResponsibleService);
    expect(service).toBeTruthy();
  });
});
