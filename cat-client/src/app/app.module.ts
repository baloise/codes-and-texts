import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CodeTypeListComponent} from './code-type-list/code-type-list.component';
import {CodeTypeFormComponent} from './code-type-form/code-type-form.component';

@NgModule({
  declarations: [
    AppComponent,
    CodeTypeListComponent,
    CodeTypeFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
