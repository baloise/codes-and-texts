import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CodeTypeListComponent} from "./code-type-list/code-type-list.component";
import {CodeTypeFormComponent} from "./code-type-form/code-type-form.component";

const routes: Routes = [
  { path: 'codetypes', component: CodeTypeListComponent },
  { path: 'addcodetype', component: CodeTypeFormComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
