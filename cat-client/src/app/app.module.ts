import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CodeTypeListComponent} from './code-type-list/code-type-list.component';
import {CodeTypeFormComponent} from './code-type-form/code-type-form.component';
import { CodeTypeDetailsComponent } from './code-type-details/code-type-details.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    CodeTypeListComponent,
    CodeTypeFormComponent,
    CodeTypeDetailsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
