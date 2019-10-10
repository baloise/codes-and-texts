import { TestBed } from '@angular/core/testing';

import { CodeValueService } from './code-value.service';

describe('CodeValueService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CodeValueService = TestBed.get(CodeValueService);
    expect(service).toBeTruthy();
  });
});
