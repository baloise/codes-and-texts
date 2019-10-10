import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeTypeListComponent } from './code-type-list.component';

describe('CodeTypeListComponent', () => {
  let component: CodeTypeListComponent;
  let fixture: ComponentFixture<CodeTypeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodeTypeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeTypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
