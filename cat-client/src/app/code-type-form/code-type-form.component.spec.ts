import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeTypeFormComponent } from './code-type-form.component';

describe('CodeTypeFormComponent', () => {
  let component: CodeTypeFormComponent;
  let fixture: ComponentFixture<CodeTypeFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodeTypeFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeTypeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
