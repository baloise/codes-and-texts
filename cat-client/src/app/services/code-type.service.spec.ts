import { TestBed } from '@angular/core/testing';

import { CodeTypeService } from './code-type.service';

describe('CodeTypeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CodeTypeService = TestBed.get(CodeTypeService);
    expect(service).toBeTruthy();
  });
});
