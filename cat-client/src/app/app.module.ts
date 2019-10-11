import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CodeTypeListComponent} from './code-type-list/code-type-list.component';
import {CodeTypeFormComponent} from './code-type-form/code-type-form.component';
import {CodeTypeDetailsComponent} from './code-type-details/code-type-details.component';
import {InlineEditComponent} from './inline-edit/inline-edit.component';
import {FormsModule} from "@angular/forms";
import {FaIconLibrary, FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {faEdit, faPlus, faSave} from "@fortawesome/free-solid-svg-icons";

@NgModule({
  declarations: [
    AppComponent,
    CodeTypeListComponent,
    CodeTypeFormComponent,
    CodeTypeDetailsComponent,
    InlineEditComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    FontAwesomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private library: FaIconLibrary) {
    library.addIcons(faEdit, faSave, faPlus);
  }
}
