import {Component, ElementRef, forwardRef, Input, OnInit, Renderer2, ViewChild} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';

@Component({
  selector: 'app-inline-edit',
  templateUrl: './inline-edit.component.html',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => InlineEditComponent),
    multi: true
  }],
  styleUrls: ['./inline-edit.component.css']
})
export class InlineEditComponent implements ControlValueAccessor, OnInit {
  @ViewChild('inlineEditControl', {static: false}) inlineEditControl: ElementRef; // input DOM element
  @Input() type = 'text'; // The type of input element
  @Input() required = false; // Is input requried?
  @Input() disabled = false; // Is input disabled?
  @Input() editing = false; // Is Component in edit mode?
  private currentValue = ''; // Private variable for input value
  private preValue = ''; // The value before clicking to edit
  public onChange: any = Function.prototype; // Trascend the onChange event
  public onTouched: any = Function.prototype; // Trascend the onTouch event

  // Control Value Accessors for ngModel
  get value(): any {
    return this.currentValue;
  }

  set value(v: any) {
    if (v !== this.currentValue) {
      this.currentValue = v;
      this.onChange(v);
    }
  }

  constructor(element: ElementRef, private renderer: Renderer2) {
  }

  // Required for ControlValueAccessor interface
  writeValue(value: any) {
    this.currentValue = value;
  }

  // Required forControlValueAccessor interface
  public registerOnChange(fn: (_: any) => {}): void {
    this.onChange = fn;
  }

  // Required forControlValueAccessor interface
  public registerOnTouched(fn: () => {}): void {
    this.onTouched = fn;
  }

  // Do stuff when the input element loses focus
  onBlur($event: Event) {
    this.editing = false;
  }

  // Start the editting process for the input element
  edit(value) {
    if (this.disabled) {
      return;
    }

    this.preValue = value;
    this.editing = true;
    // Focus on the input element just as the editing begins
    setTimeout(_ => this.inlineEditControl.nativeElement.focus());
  }

  ngOnInit() {
  }
}
