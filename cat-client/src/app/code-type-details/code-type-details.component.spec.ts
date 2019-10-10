import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeTypeDetailsComponent } from './code-type-details.component';

describe('CodeTypeDetailsComponent', () => {
  let component: CodeTypeDetailsComponent;
  let fixture: ComponentFixture<CodeTypeDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodeTypeDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeTypeDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
