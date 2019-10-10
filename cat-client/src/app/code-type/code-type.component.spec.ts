import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeTypeComponent } from './code-type.component';

describe('CodeTypeComponent', () => {
  let component: CodeTypeComponent;
  let fixture: ComponentFixture<CodeTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodeTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
